/**
 * Created on 21.10.2002 23:42:26
 *
 */
package org.wewi.medimg.image;

import java.util.RandomAccess;

/**
 * Abstract implementation of the <code>Image</code>-interface.
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class AbstractImage extends RowMajorImageGeometry
                                    implements Image, RandomAccess {

    private final class AbstractImageVoxelIterator implements VoxelIterator {
        private int pos = 0;
        
        public AbstractImageVoxelIterator() {
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
        colorConversion = new GreyColorConversion();
    }
    
    public AbstractImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this(new Dimension(minX, maxX, minY, maxY, minZ, maxZ));
    }
     
    public AbstractImage(int sizeX, int sizeY, int sizeZ) {
        this(new Dimension(sizeX, sizeY, sizeZ));
    }
    
    void init(Dimension dim, AbstractImageHeader header) {
        super.init(dim);
        this.header = header;
        
        data = createDiscreteData(size);  
    }  
    
    protected abstract DiscreteData createDiscreteData(int size);    
   
    public int getColor(int x, int y, int z) {
        return data.get(getPosition(x, y, z));
    }
    
    public int getIntColor(int x, int y, int z) {
        return data.getInt(getPosition(x, y, z));
    }
    
    public double getDoubleColor(int x, int y, int z) {
        return data.getDouble(getPosition(x, y, z));
    }
    
    public int getColor(int pos) {
        return data.get(pos);
    }
    
    public int getIntColor(int pos) {
        return data.getInt(pos);
    }
    
    public double getDoubleColor(int pos) {
        return data.getDouble(pos);
    }
    
    public void setColor(int x, int y, int z, int color) {
        data.set(getPosition(x, y, z), color);
    }
    
    public void setColor(int x, int y, int z, double color) {
        data.set(getPosition(x, y, z), color);
    }
    
    public void setColor(int pos, int color) {
        data.set(pos, color);
    } 
    
    public void setColor(int pos, double color) {
        data.set(pos, color);
    }

    public void resetColor(int color) {
        data.fill(color);
    } 
    
    public void resetColor(double color) {
        data.fill(color);     
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
