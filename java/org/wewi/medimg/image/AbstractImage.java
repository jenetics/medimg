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
            return data.getIntValue(pos++);
        }
        
        public int next(int[] p) {
            int c = data.getIntValue(pos);
            getCoordinates(pos, p);
            ++pos;
            return c;
        }
        
        public int next(double[] p) {
            int c = data.getIntValue(pos);
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
    
    private ImageData data;
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
        data = createImageData(size);
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
        
        data = createImageData(size);  
    }  
    
    protected abstract ImageData createImageData(int size);    
   
    public int getColor(int x, int y, int z) {
        return data.getIntValue(getPosition(x, y, z));
    }
    
    public int getIntColor(int x, int y, int z) {
        return data.getIntValue(getPosition(x, y, z));
    }
    
    public double getDoubleColor(int x, int y, int z) {
        return data.getDoubleValue(getPosition(x, y, z));
    }
    
    public int getColor(int pos) {
        return data.getIntValue(pos);
    }
    
    public int getIntColor(int pos) {
        return data.getIntValue(pos);
    }
    
    public double getDoubleColor(int pos) {
        return data.getDoubleValue(pos);
    }
    
    public void setColor(int x, int y, int z, int color) {
        data.setValue(getPosition(x, y, z), color);
    }
    
    public void setColor(int x, int y, int z, double color) {
        data.setValue(getPosition(x, y, z), color);
    }
    
    public void setColor(int pos, int color) {
        data.setValue(pos, color);
    } 
    
    public void setColor(int pos, double color) {
        data.setValue(pos, color);
    }

    public void resetColor(int color) {
        data.fill(color);
    } 
    
    public void resetColor(double color) {
        data.fill(color);     
    }
    
    //New Methods for refactoring //////////////////////////////////////////////
    
    public void setColor(int pos, byte color) {
        data.setValue(pos, color);
    }
    
    public void setColor(int pos, short color) {
        data.setValue(pos, color);
    }
    
    //public void setColor(int pos, int color) {
    //    data.setValue(pos, color);
    //}
    
    public void setColor(int pos, long color) {
        data.setValue(pos, color);
    }
    
    public void setColor(int pos, float color) {
        data.setValue(pos, color);
    }
    
    //public void setColor(int pos, double color) {
    //    data.setValue(pos, color);
    //}
    
    public byte getByteColor(int pos) {
        return data.getByteValue(pos);
    }
    
    public short getShortColor(int pos) {
        return data.getShortValue(pos);
    }
    
    //public int getIntColor(int pos) {
    //    return data.getIntValue(pos);
    //}
    
    public long getLongColor(int pos) {
        return data.getLongValue(pos);
    }
    
    public float getFloatColor(int pos) {
        return data.getFloatValue(pos);
    }
    
    //public double getDoubleColor(int pos) {
    //    return data.getDoubleValue(pos);
    //}
    
    public void fill(byte color) {
        data.fill(color);
    }
    
    public void fill(short color) {
        data.fill(color);
    }
    
    public void fill(int color) {
        data.fill(color);
    }
    
    public void fill(long color) {
        data.fill(color);
    }
    
    public void fill(float color) {
        data.fill(color);
    }
    
    public void fill(double color) {
        data.fill(color);  
    }
    
    public ImageGeometry getImageGeometry() {
        return this;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
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
