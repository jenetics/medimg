/*
 * FeatureTypeData.java
 *
 * Created on 02. April 2002, 23:26
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TissueImage implements Image {
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    private int nvoxels;
    private Tissue[] tissue;
    private Tissue[] oldTissue;

    public TissueImage(int sizeX, int sizeY, int sizeZ) {
        minX = minY = minZ;
        maxX = sizeX;
        maxY = sizeY;
        maxZ = sizeZ;
        nvoxels = sizeX*sizeY*sizeZ;
        tissue = new Tissue[nvoxels];
        oldTissue = new Tissue[nvoxels];
    }
    
    public void setColor(int pos, int color) {
    }
    
    public void setColor(int x, int y, int z, int color) {
    }
    
    public int getColor(int x, int y, int z) {
        return getTissue(x, y, z).getCode();
    }
    
    public int getColor(int pos) {
        return getTissue(pos).getCode();
    }
    
    public void setTissue(int pos, Tissue t) {
        oldTissue[pos] = tissue[pos];
        tissue[pos] = t;
    }
    
    public void setTissue(int x, int y, int z, Tissue t) {
        int pos = (maxX*maxY*z + maxX*y + x);
        oldTissue[pos] = tissue[pos];
        tissue[pos] = t;
    }
    
    public Tissue getTissue(int pos) {
        return tissue[pos];
    }
    
    public Tissue getTissue(int x, int y, int z) {
        return tissue[maxX*maxY*z + maxX*y + x];
    }
    
    public boolean isNull() {
        return false;
    }
    
    public int getNVoxels() {
        return nvoxels;
    }
    
    public int getMinZ() {
        return minZ;
    }
    
    public int getMaxZ() {
        return maxZ;
    }
    
    public int getMinY() {
        return minY;
    }
    
    public int getMaxY() {
        return maxY;
    }
    
    public int getMinX() {
        return minX;
    }
    
    public int getMaxX() {
        return maxX;
    }
    
    public ImageHeader getHeader() {
        return null;
    }    
    
    public Object clone() {
        return null;
    }
}
