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
public abstract class AbstractImage implements Image, RandomAccess {

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
    
    private Dimension dimension;
    private int maxX, maxY, maxZ;
    private int minX, minY, minZ;
    private int sizeX, sizeY, sizeZ;
    private int sizeXY;
    private int size;    
    
    
    
    AbstractImage() {
        colorConversion = new GreyColorConversion();
    } 
    
    protected AbstractImage(AbstractImage id) {
        this(id.dimension);
        id.data.copy(this.data);
    }       
    
    public AbstractImage(Dimension dim) {
        this(dim.getMinX(), dim.getMaxX(),
              dim.getMinY(), dim.getMaxY(),
              dim.getMinZ(), dim.getMaxZ());
    }
    
    public AbstractImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        init(minX, maxX, minY, maxY, minZ, maxZ, new AbstractImageHeader(this));
    }
     
    public AbstractImage(int sizeX, int sizeY, int sizeZ) {
        init(0, sizeX-1, 0, sizeY-1, 0, sizeZ-1, new AbstractImageHeader(this));
    }
    
    void init(Dimension dim, AbstractImageHeader header) {
        init(dim.getMinX(), dim.getMaxX(),
             dim.getMinY(), dim.getMaxY(),
             dim.getMinZ(), dim.getMaxZ(), header);    
    }
   
    void init(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, AbstractImageHeader header) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
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
    
    public int getMaxX() {
        return maxX;
    }
    
    public int getMaxY() {
        return maxY;
    }
    
    public int getMaxZ() {
        return maxZ;
    }
    
    public int getMinX() {
        return minX;
    }
    
    public int getMinY() {
        return minY;
    }
    
    public int getMinZ() {
        return minZ;
    }   
    
    public Dimension getDimension() {
        return dimension;   
    } 
    
    public int getNVoxels() {
        return size;
    }
    
    public boolean isNull() {
        return false;
    }
    
    public abstract Object clone();
    
    public int getPosition(int x, int y, int z) {
        return (sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX));
    }
    
    public int[] getCoordinates(int pos) {
        int[] erg = new int[3];
        getCoordinates(pos, erg);
        return erg;
    } 
    
    
    public void getCoordinates(int pos, int[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = pos - (coordinate[2] * sizeXY);
        coordinate[1] = pos / (sizeX);
        pos = pos - (coordinate[1] * sizeX);
        coordinate[0] = pos; 
        
        coordinate[0] += minX;
        coordinate[1] += minY;
        coordinate[2] += minZ;
    } 
    
    private void getCoordinates(int pos, double[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = (int)(pos - (coordinate[2] * sizeXY));
        coordinate[1] = pos / (sizeX);
        pos = (int)(pos - (coordinate[1] * sizeX));
        coordinate[0] = pos; 
        
        coordinate[0] += minX;
        coordinate[1] += minY;
        coordinate[2] += minZ;
    }    
    
    public void getNeighbor3D12Positions(int pos, int[] n12) {
        n12[0] = pos - 1 - sizeXY;
        n12[1] = pos - 1 + sizeXY;
        n12[2] = pos - 1 - sizeX;
        n12[3] = pos - 1 + sizeX;
        n12[4] = pos + 1 - sizeXY;
        n12[5] = pos + 1 + sizeXY;
        n12[6] = pos + 1 - sizeX;
        n12[7] = pos + 1 + sizeX;
        n12[8] = pos - sizeX - sizeXY;
        n12[9] = pos - sizeX + sizeXY;
        n12[10] = pos + sizeX - sizeXY;
        n12[11] = pos + sizeX + sizeXY;         
    }
    
    public void getNeighbor3D18Positions(int pos, int[] n18) {
        n18[0] = pos - 1;
        n18[1] = pos + 1;
        n18[2] = pos - sizeX;
        n18[3] = pos + sizeX;
        n18[4] = pos - sizeXY;
        n18[5] = pos + sizeXY;  
        n18[6] = pos - 1 - sizeXY;
        n18[7] = pos - 1 + sizeXY;
        n18[8] = pos - 1 - sizeX;
        n18[9] = pos - 1 + sizeX;
        n18[10] = pos + 1 - sizeXY;
        n18[11] = pos + 1 + sizeXY;
        n18[12] = pos + 1 - sizeX;
        n18[13] = pos + 1 + sizeX;
        n18[14] = pos - sizeX - sizeXY;
        n18[15] = pos - sizeX + sizeXY;
        n18[16] = pos + sizeX - sizeXY;
        n18[17] = pos + sizeX + sizeXY;         
    }
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
        n6[0] = pos - 1;
        n6[1] = pos + 1;
        n6[2] = pos - sizeX;
        n6[3] = pos + sizeX;
        n6[4] = pos - sizeXY;
        n6[5] = pos + sizeXY;         
    }    
    
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
