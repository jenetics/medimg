/**
 * Created on 21.10.2002 23:42:26
 *
 */
package org.wewi.medimg.image;

import java.util.RandomAccess;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class AbstractImage extends LineScanImageGeometry
                                      implements Image, RandomAccess {

    private final class AbstractImageVoxelIterator implements VoxelIterator {
        private int pos;
        
        public AbstractImageVoxelIterator() {
            pos = 0;
        }
        
        public boolean hasNext() {
            return pos < size;
        }
        
        public int next() {
            return data.get(pos++);
        }
        
        public int next(int[] p) {
            int c = data.get(pos);
            getCoordinates(pos, p);
            ++pos;
            return c;
        }
        
        public int next(double[] p) {
            int c = data.get(pos);
            getCoordinates(pos, p);
            ++pos;
            return c;
        }        
        
        public int size() {
            return size;
        }
        
        public Object clone() {
            return new AbstractImageVoxelIterator();
        }
    } 
    /**************************************************************************/
    
    private DiscreteData data;
    private AbstractImageHeader header;
    
    private ColorRange colorRange = null;
    private int minColor = Integer.MIN_VALUE;
    private int maxColor = Integer.MAX_VALUE;
    private ColorConversion colorConversion; 
    
    
    AbstractImage() {
        colorConversion = new GreyColorConversion();
    } 
    
    protected AbstractImage(AbstractImage id) {
        this(id.dimension);
        id.data.copy(this.data);
    }       
    
    public AbstractImage(Dimension dim) {
        super(dim);
        header = new AbstractImageHeader(this);
        data = createDiscreteData(size);
    }
    
    public AbstractImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this(new Dimension(minX, maxX, minY, maxY, minZ, maxZ));
    }
     
    public AbstractImage(int sizeX, int sizeY, int sizeZ) {
        this(new Dimension(sizeX, sizeY, sizeZ));
    }
    
    void init(Dimension dim, AbstractImageHeader header) {
        init(dim.getMinX(), dim.getMaxX(),
             dim.getMinY(), dim.getMaxY(),
             dim.getMinZ(), dim.getMaxZ(), header);    
    }
   
    void init(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, AbstractImageHeader header) {

        this.header = header;
        
        dimension = new Dimension(minX, maxX, minY, maxY, minZ, maxZ);
        colorConversion = new GreyColorConversion();         
        
        sizeX = maxX - minX + 1;
        sizeY = maxY - minY + 1;
        sizeZ = maxZ - minZ + 1;
        size = sizeX*sizeY*sizeZ;
        sizeXY = sizeX*sizeY;        
        
        data = createDiscreteData(size);   
    }   
    
    protected abstract DiscreteData createDiscreteData(int size);    
   
    public int getColor(int x, int y, int z) {
        return data.get(sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX));
    }
    
    public int getColor(int pos) {
        return data.get(pos);
    }
    
    public void setColor(int x, int y, int z, int color) {
        data.set(sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX), color);
    }
    
    public final void setColor(int pos, int color) {
        data.set(pos, color);
    } 

    public void resetColor(int color) {
        data.fill(color);
    }  
    
    protected void updateColorRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (min > data.get(i)) {
                min = data.get(i);
            } else if (max < data.get(i)) {
                max = data.get(i);
            }
        }
        minColor = min;
        maxColor = max;
        colorRange = new ColorRange(minColor, maxColor);
    }
    
    public ColorRange getColorRange() {
        if (colorRange == null) {
            updateColorRange();
        }
        return colorRange;
    }
    
    public int getMaxColor() {
        return maxColor;
    }
    
    public int getMinColor() {
        return minColor;
    }    
    
    public boolean isNull() {
        return false;
    }
    
    public abstract Object clone();
       
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName() + ":\n    ");
        buffer.append("(").append(minX);
        buffer.append(",").append(minY);
        buffer.append(",").append(minZ);
        buffer.append("),(").append(maxX);
        buffer.append(",").append(maxY);
        buffer.append(",").append(maxZ).append(")");
        return buffer.toString();
    }
    
    public ImageHeader getHeader() {
        return header;
    }   
    
    public ColorConversion getColorConversion() {
        return colorConversion; 
    }
    
    public void setColorConversion(ColorConversion cc) {
        colorConversion = cc;   
    }

    public VoxelIterator getVoxelIterator() {
        return new AbstractImageVoxelIterator();
    }
}
