/*
 * ImageXSlice.java
 *
 * Created on 10. April 2002, 14:03
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class ImageXSlice implements Image {
    private Image image;
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    
    /** Creates a new instance of ImageXSlice */
    public ImageXSlice(Image image) {
        this.image = image;
        minX = image.getMinZ(); maxX = image.getMaxZ();
        minY = image.getMinX(); maxY = image.getMaxX();
        minZ = image.getMinY(); maxZ = image.getMaxY();
    }
    
    public int getColor(int pos) {
        return image.getColor(pos);
    }
    
    public int getColor(int x, int y, int z) {
        return image.getColor(z, x, y);
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
        return image.getNVoxels();
    }
    
    public boolean isNull() {
        return image.isNull();
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
        image.setColor(z, x, y, color);
    }
    
    public Object clone() {
        Image clone = (Image)image.clone();
        return new ImageXSlice(clone);
    }
    
    public int[] getCoordinates(int pos) {
        return null;
    }
    
    public int getPosition(int x, int y, int z) {
        return 0;
    }
    
    public void resetColor(int color) {
    }
    
    public void getCoordinates(int pos, int[] coordinates) {
    }
    
    public VoxelIterator getVoxelIterator() {
        return null;
    }
    
    public ColorRange getColorRange() {
        return null;
    }
    
    public int getMaxColor() {
        return 0;
    }
    
    public int getMinColor() {
        return 0;
    }
    
    public void getNeighbor3D12Positions(int pos, int[] n12) {
    }
    
    public void getNeighbor3D18Positions(int pos, int[] n18) {
    }
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
    }
    
}
