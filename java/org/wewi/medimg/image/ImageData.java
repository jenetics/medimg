/*
 * ImageData.java
 *
 * Created on 5. Dezember 2001, 14:45
 */

package org.wewi.medimg.image;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ImageData implements Image {
    private int maxX, maxY, maxZ;
    private int minX, minY, minZ;
    private int sizeX, sizeY, sizeZ;
    private int size;
    private short[] data;
    
    private ImageDataHeader header;
    
    ImageData() {
    }
    
    private ImageData(ImageData id) {
        this(id.maxX, id.maxY, id.maxZ);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    void init(int sizeX, int sizeY, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        maxX = sizeX-1;
        maxY = sizeY-1;
        maxZ = sizeZ-1;
        minX = minY = minZ = 0;
        size = sizeX*sizeY*sizeZ;
        data = new short[size]; 
        Arrays.fill(data, (short)0);
    }
    
    public ImageData(int sizeX, int sizeY, int sizeZ) {
        init(sizeX, sizeY, sizeZ);
        header = new ImageDataHeader(sizeX, sizeY, sizeZ, this);
    }
   
    public int getColor(int x, int y, int z) {
        try {
            return data[sizeX*sizeY*z + sizeX*y + x];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("(" + maxX + "," + maxY + "," + maxZ +")(" + x + "," + y + "," + z + ")");
            throw new ArrayIndexOutOfBoundsException("" + e);
        }
    }
    
    public int getColor(int pos) {
        return data[pos];
    }
    
    public void setColor(int x, int y, int z, int color) {
        data[sizeX*sizeY*z + sizeX*y + x] = (short)color;
    }
    
    public void setColor(int pos, int color) {
        data[pos] = (short)color;
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
    
    public int getNVoxels() {
        return size;
    }
    
    public boolean isNull() {
        return false;
    }
    
    public Object clone() {
        return new ImageData(this);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
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
    
}



