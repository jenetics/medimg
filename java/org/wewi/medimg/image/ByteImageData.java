/**
 * LightSegmentationImage.java
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
        super(id.maxX + 1, id.maxY + 1, id.maxZ + 1);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    public ByteImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ByteImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
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
