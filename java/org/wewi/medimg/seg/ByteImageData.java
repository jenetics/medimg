/*
 * LightSegmentationImage.java
 *
 * Created on 10. Mai 2002, 15:59
 */

package org.wewi.medimg.seg;

import java.util.Arrays;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageHeader;
import org.wewi.medimg.image.VoxelIterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ByteImageData implements Image {
    
    private class ByteImageDataVoxelIterator implements VoxelIterator {
        private byte[] data;
        private final int size;
        private int pos;
        
        public ByteImageDataVoxelIterator(byte[] data) {
            this.data = data;
            size = data.length;
            pos = 0;
        }
        
        public boolean hasNext() {
            return pos < size;
        }
        
        public int next() {
            return data[pos++];
        }
        
        public int size() {
            return size;
        }
        public Object clone() {
            return new ByteImageDataVoxelIterator(data);
        }
    }
    
    private byte[] data;
    
    private int minColor;
    private int maxColor;
    private ColorRange colorRange = null;
    private int maxX, maxY, maxZ;
    private int minX, minY, minZ;
    private Dimension dimension;
    private int size, sizeX, sizeY, sizeZ, sizeXY; 

    public ByteImageData(int sizeX, int sizeY, int sizeZ) {
        this(0, 0, 0, sizeX-1, sizeY-1, sizeZ-1);
    }
    
    public ByteImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        sizeX = maxX-minX+1;
        sizeY = maxY-minY+1;
        sizeZ = maxZ-minZ+1;
        sizeXY = sizeX*sizeY;
        size = sizeX*sizeY*sizeZ;
        dimension = new Dimension(minX, maxX, minY, maxY, minZ, maxZ);
        
        data = new byte[size];
        Arrays.fill(data, (byte)0);
    }
    
    public int getColor(int pos) {
        return data[pos];
    }
    
    public int getColor(int x, int y, int z) {
        return data[sizeXY*z + sizeX*y + x];
    }
    
    private void findColorRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (min > data[i]) {
                min = data[i];
            } else if (max < data[i]) {
                max = data[i];
            }
        }
        minColor = min;
        maxColor = max;
        colorRange = new ColorRange(minColor, maxColor);
    }    
    
    public ColorRange getColorRange() {
        if (colorRange == null) {
            findColorRange();
        }
        return colorRange;
    }
    
    public int getMinColor() {
        return minColor;
    }
    
    public int getMaxColor() {
        return maxColor;
    }
    
    public int[] getCoordinates(int pos) {
        int[] erg = new int[3];
        erg[2] = pos / (sizeXY);
        pos = pos - (erg[2] * sizeXY);
        erg[1] = pos / (sizeX);
        pos = pos - (erg[1] * sizeX);
        erg[0] = pos;
        return erg;
    }  
    
    public void getCoordinates(int pos, int[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = pos - (coordinate[2] * sizeXY);
        coordinate[1] = pos / (sizeX);
        pos = pos - (coordinate[1] * sizeX);
        coordinate[0] = pos;        
    }
    
    public ImageHeader getHeader() {
        return new org.wewi.medimg.image.NullImageHeader();
    }
    
    public ColorConversion getColorConversion() {
    	return null;	
    }
    
    public void setColorConversion(ColorConversion cc) {	
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
    
    public int getPosition(int x, int y, int z) {
        return sizeXY*z + sizeX*y + x;
    }
    
    public VoxelIterator getVoxelIterator() {
        return new ByteImageDataVoxelIterator(data);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void resetColor(int color) {
        Arrays.fill(data, (byte)color);
    }
    
    public void setColor(int pos, int color) {
        data[pos] = (byte)color;
    }
    
    public void setColor(int x, int y, int z, int color) {
        data[sizeXY*z + sizeX*y + x] = (byte)color;
    }
 
    public Object clone() {
        return new ByteImageData(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public void getNeighbor3D12Positions(int pos, int[] n12) {
    }
    
    public void getNeighbor3D18Positions(int pos, int[] n18) {
    }
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
    }
    
}
