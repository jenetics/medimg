/**
 * ByteImageData.java
 *
 * Created on 10. Mai 2002, 15:59
 */

package org.wewi.medimg.image;

import java.util.Arrays;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ByteImageData extends ImageData {
    private byte[] data;   
     
    ByteImageData() {
        super();
    }     
     
    ByteImageData(ByteImageData id) {
        super(id.sizeX, id.sizeY, id.sizeZ);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    public ByteImageData(Dimension dim) {
        super(dim);    
    }
    
    public ByteImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ByteImageData(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }    
     

    protected void initData() {
        data = new byte[size];    
    }
    
    protected void setData(int pos, int color) {
        data[pos] = (byte)color;    
    }
    
    protected int getData(int pos) {
        return data[pos];    
    }
    
    public void resetColor(int color) {
        Arrays.fill(data, (byte)color);
    }    
    
    public Object clone() {
        return new ByteImageData(this);
    }    
    
}
