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
    private int sizeXY, size;
    private short[] data;
    
    private ImageDataHeader header;
    
    ImageData() {
    }
    
    private ImageData(ImageData id) {
        this(id.maxX, id.maxY, id.maxZ);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    void init(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        sizeX = maxX - minX + 1;
        sizeY = maxY - minY + 1;
        sizeZ = maxZ - minZ + 1;
        size = sizeX*sizeY*sizeZ;
        sizeXY = sizeX*sizeY;
        data = new short[size]; 
        Arrays.fill(data, (short)0);
        
        header = new ImageDataHeader(minX, minY, minZ, maxX, maxY, maxZ, this);
    }
    
    public ImageData(int sizeX, int sizeY, int sizeZ) {
        init(0, 0, 0, sizeX-1, sizeY-1, sizeZ-1);
    }
    
    public ImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        init(minX, minY, minZ, maxX, maxY, maxZ);
    }
   
    public int getColor(int x, int y, int z) {
        return data[sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX)];
    }
    
    public int getColor(int pos) {
        return data[pos];
    }
    
    public void setColor(int x, int y, int z, int color) {
        data[sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX)] = (short)color;
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
        buffer.append("ImageData:\n    ");
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



