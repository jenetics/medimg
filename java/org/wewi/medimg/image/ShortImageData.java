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
        super(id.maxX + 1, id.maxY + 1, id.maxZ + 1);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    public ShortImageData(Dimension dim) {
        super(dim);    
    }
    
    public ShortImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ShortImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
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
