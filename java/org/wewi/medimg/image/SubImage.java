/*
 * SubImage.java
 *
 * Created on 4. Juni 2002, 20:54
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SubImage implements Image {
    private Image image;
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    private int sizeX, sizeY, sizeZ, nvoxel;
    
    //Variable für die Berechnung der Koordinaten aus der Position
    private int[] coord = new int[3];
    
    /** Creates a new instance of SubImage */
    public SubImage(Image image, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.image = image;
        
        this.minX = Math.max(image.getMinX(), minX);
        this.minY = Math.max(image.getMinY(), minY);
        this.minZ = Math.max(image.getMinZ(), minZ);
        this.maxX = Math.min(image.getMaxX(), maxX);
        this.maxY = Math.min(image.getMaxY(), maxY);
        this.maxZ = Math.min(image.getMaxZ(), maxZ);
        
        sizeX = this.maxX - this.minX + 1;
        sizeY = this.maxY - this.minY + 1;
        sizeZ = this.maxZ - this.minZ + 1;
        nvoxel = sizeX*sizeY*sizeZ;
    }
    
    public int getColor(int pos) {
        return image.getColor(pos);
    }
    
    public int getColor(int x, int y, int z) {
        return image.getColor(x, y, z);
    }
    
    public int[] getCoordinates(int pos) {
        return image.getCoordinates(pos);
    }
    
    public void getCoordinates(int pos, int[] coordinate) {
        image.getCoordinates(pos, coordinate);
    }
    
    public ImageHeader getHeader() {
        return image.getHeader();
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
        return nvoxel;
    }
    
    public int getPosition(int x, int y, int z) {
        return image.getPosition(x, y, z);
    }
    
    public VoxelIterator getVoxelIterator() {
        return image.getVoxelIterator();
    }
    
    public boolean isNull() {
        return image.isNull();
    }
    
    public void resetColor(int color) {
        image.resetColor(color);
    }
    
    /**
     * Setzen des Grauwertes eines Bildpunktes an den angegebene Stelle.
     * Ein Bildpunkt kann anstatt über seine Koordinaten auch über dessen
     * Posittion angesprochen werden. Diese Zugriffsmethode ist dann
     * zu bevorzugen, wenn die räumliche Lage des Bildementes keine Rolle
     * spielt. Ein Zugriff über die Position ist in der Regel schneller
     * als über die Koordinaten. Die Werte für die Position liegen zwischen
     * 0 und maxX*maxY*maxZ-1
     *
     * @param pos Position des Bildpunktes
     * @param color Zu setzender Grauwert
     */
    public void setColor(int pos, int color) {
        image.setColor(pos, color);
    }
    
    /**
     * Setzen des Grauwertes eines Bildpunktes an den angegebene
     * Koordinaten.
     *
     * @param x X- Koordinaten des Bildpunktes
     * @param y Y-Koordinate des Bildpunktes
     * @param z Z-Koordinate des Bildpunktes
     * @param color zu setzender Grauwert
     */
    public void setColor(int x, int y, int z, int color) {
        image.setColor(x, y, z, color);
    }
    
    public Object clone() {
        return new SubImage(image, minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public ColorRange getColorRange() {
        return image.getColorRange();
    }
    
    public int getMaxColor() {
        return image.getMaxColor();
    }
    
    public int getMinColor() {
        return image.getMinColor();
    }
    
    public void getNeighbor3D12Positions(int pos, int[] n12) {
    }
    
    public void getNeighbor3D18Positions(int pos, int[] n18) {
    }
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
    }
    
}
