/**
 * ImageAdapter.java
 * 
 * Created on 17.12.2002, 13:12:12
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageAdapter implements Image {
    protected Image image;

    /**
     * Constructor for ImageAdapter.
     */
    protected ImageAdapter() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.Image#getHeader()
     */
    public ImageHeader getHeader() {
        return image.getHeader();
    }

    /**
     * @see org.wewi.medimg.image.Image#getColorConversion()
     */
    public ColorConversion getColorConversion() {
        return image.getColorConversion();
    }

    /**
     * @see org.wewi.medimg.image.Image#setColorConversion(ColorConversion)
     */
    public void setColorConversion(ColorConversion cc) {
        image.setColorConversion(cc);
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return image.clone();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMaxX()
     */
    public int getMaxX() {
        return image.getMaxX();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMaxY()
     */
    public int getMaxY() {
        return image.getMaxY();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMaxZ()
     */
    public int getMaxZ() {
        return image.getMaxZ();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMinX()
     */
    public int getMinX() {
        return image.getMinX();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMinY()
     */
    public int getMinY() {
        return image.getMinY();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getMinZ()
     */
    public int getMinZ() {
        return image.getMinY();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getNVoxels()
     */
    public int getNVoxels() {
        return image.getNVoxels();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getDimension()
     */
    public Dimension getDimension() {
        return image.getDimension();
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getPosition(int, int, int)
     */
    public int getPosition(int x, int y, int z) {
        return image.getPosition(x, y, z);
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getCoordinates(int)
     */
    public int[] getCoordinates(int pos) {
        return image.getCoordinates(pos);
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getCoordinates(int, int[])
     */
    public void getCoordinates(int pos, int[] coordinate) {
        image.getCoordinates(pos, coordinate);
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getNeighbor3D12Positions(int, int[])
     */
    public void getNeighbor3D12Positions(int pos, int[] n12) {
        image.getNeighbor3D12Positions(pos, n12);
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getNeighbor3D6Positions(int, int[])
     */
    public void getNeighbor3D6Positions(int pos, int[] n6) {
        image.getNeighbor3D6Positions(pos, n6);
    }

    /**
     * @see org.wewi.medimg.image.ImageGeometry#getNeighbor3D18Positions(int, int[])
     */
    public void getNeighbor3D18Positions(int pos, int[] n18) {
        image.getNeighbor3D18Positions(pos, n18);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#setColor(int, int, int, int)
     */
    public void setColor(int x, int y, int z, int color) {
        image.setColor(x, y, z, color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#setColor(int, int)
     */
    public void setColor(int pos, int color) {
        image.setColor(pos, color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#resetColor(int)
     */
    public void resetColor(int color) {
        image.resetColor(color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getColor(int)
     */
    public int getColor(int pos) {
        return image.getColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getColor(int, int, int)
     */
    public int getColor(int x, int y, int z) {
        return image.getColor(x, y, z);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getVoxelIterator()
     */
    public VoxelIterator getVoxelIterator() {
        return image.getVoxelIterator();
    }

    /**
     * @see org.wewi.medimg.util.Nullable#isNull()
     */
    public boolean isNull() {
        return false;
    }

}
