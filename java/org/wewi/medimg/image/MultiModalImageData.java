/*
 * MultiModalImageData.java
 *
 * Created on 1. Juli 2002, 20:58
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MultiModalImageData implements MultiModalImage {
    

    public MultiModalImageData() {
    }
    
    public int getColor(int pos) {
        return 0;
    }
    
    public int getColor(int x, int y, int z) {
        return 0;
    }
    
    public int getColor(int x, int y, int z, int m) {
        return 0;
    }
    
    public ColorRange getColorRange() {
        return null;
    }
    
    public int[] getCoordinates(int pos) {
        return null;
    }
    
    public void getCoordinates(int pos, int[] coordinate) {
    }
    
    public ImageHeader getHeader() {
        return null;
    }
    
    public int getMaxColor() {
        return 0;
    }
    
    public int getMaxX() {
        return 0;
    }
    
    public int getMaxY() {
        return 0;
    }
    
    public int getMaxZ() {
        return 0;
    }
    
    public int getMinColor() {
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
    
    public int getNoOfModality() {
        return 0;
    }
    
    public int getPosition(int x, int y, int z) {
        return 0;
    }
    
    public VoxelIterator getVoxelIterator() {
        return null;
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void resetColor(int color) {
    }
    
    public void setColor(int pos) {
    }

    public void setColor(int pos, int color) {
    }
    
    public void setColor(int x, int y, int z, int color) {
    }
    
    public void setColor(int x, int y, int z, int m, int color) {
    }
    
    public Object clone() {
        return null;
    }
    
}
