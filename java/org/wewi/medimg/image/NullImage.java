/*
 * NullImage.java
 *
 * Created on 26. Jänner 2002, 21:08
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Nullable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class NullImage implements Image, Nullable {
    
    private class NullImageVoxelIterator implements VoxelIterator {
        public boolean hasNext() {
            return false;
        }
        
        public int next() {
            return 0;
        }
        
        public int size() {
            return 0;
        }
        public Object clone() {
            return new NullImageVoxelIterator();
        }
    }
    
    
    private NullImageHeader header;

    public NullImage() {
        header = new NullImageHeader();
    }
    
    public int getColor(int x, int y, int z) {
        return 0;
    }
    
    public int getColor(int pos) {
        return 0;
    }
    
    public void setColor(int pos, int color) {
    }
    
    public void setColor(int x, int y, int z, int color) {
    }
    
    public int getMaxZ() {
        return 0;
    }
    
    public int getMaxY() {
        return 0;
    }
    
    public int getMaxX() {
        return 0;
    }
    
    public int getMinX() {
        return 0;
    }
    
    public int getMinY() {
        return 0;
    }
    
    public int getMinZ() {
        return 0;
    }    
    
    public int getNVoxels() {
        return 0;
    }
    
    public boolean isNull() {
        return true;
    }
    
    public Object clone() {
        return new NullImage();
    }    
    
    public ImageHeader getHeader() {
        return header;
    }
    
    public int getPosition(int x, int y, int z) {
        return 0;
    }
    
    public void resetColor(int color) {
    }
    
    public int[] getCoordinates(int pos) {
        int[] retVal = {};
        return retVal;
    }
    
    public void getCoordinates(int pos, int[] coordinates) {
    }
    
    public VoxelIterator getVoxelIterator() {
        return new NullImageVoxelIterator();
    }
    
}
