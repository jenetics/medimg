/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ShortImageData extends ImageData {
    private short[] data;   
     
    ShortImageData() {
        super();
    }     
     
    ShortImageData(ShortImageData id) {
        super(id.sizeX, id.sizeY, id.sizeZ);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    public ShortImageData(Dimension dim) {
        super(dim);    
    }
    
    public ShortImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ShortImageData(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }    
     

    protected void initData() {
        data = new short[size];    
    }
    
    protected void setData(int pos, int color) {
        data[pos] = (short)color;    
    }
    
    protected int getData(int pos) {
        return data[pos];    
    }
    
    public void resetColor(int color) {
        Arrays.fill(data, (short)color);
    }    
    
    
    public Object clone() {
        return new ShortImageData(this);
    }    
}
