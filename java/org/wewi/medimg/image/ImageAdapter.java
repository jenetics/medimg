/* 
 * ImageAdapter.java, created on 17.12.2002, 13:12:12
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
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
    
    public int getPosition(int[] coordinate) {
        return image.getPosition(coordinate);
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
    
    public void setColor(int x, int y, int z, double color) {
        image.setColor(x, y, z, color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#setColor(int, int)
     */
    public void setColor(int pos, int color) {
        image.setColor(pos, color);
    }
    
    public void setColor(int pos, double color) {
        image.setColor(pos, color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#resetColor(int)
     */
    public void resetColor(int color) {
        image.resetColor(color);
    }
    
    public void resetColor(double color) {
        image.resetColor(color);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getColor(int)
     */
    public int getColor(int pos) {
        return image.getColor(pos);
    }
    
    public int getIntColor(int pos) {
        return image.getIntColor(pos);
    }
    
    public double getDoubleColor(int pos) {
        return image.getDoubleColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getColor(int, int, int)
     */
    public int getColor(int x, int y, int z) {
        return image.getColor(x, y, z);
    }
    
    public int getIntColor(int x, int y, int z) {
        return image.getIntColor(x, y, z);
    }
    
    public double getDoubleColor(int x, int y, int z) {
        return image.getDoubleColor(x, y, z);
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getVoxelIterator()
     */
    public VoxelIterator getVoxelIterator() {
        return image.getVoxelIterator();
    }
    
    public PixelIterator iterator() {
        return image.iterator();
    }

    /**
     * @see org.wewi.medimg.util.Nullable#isNull()
     */
    public boolean isNull() {
        return image.isNull();
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, byte)
     */
    public void setColor(int pos, byte color) {
        image.setColor(pos, color);
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, short)
     */
    public void setColor(int pos, short color) {
        image.setColor(pos, color);
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, long)
     */
    public void setColor(int pos, long color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, float)
     */
    public void setColor(int pos, float color) {
        image.setColor(pos, color);
    }

    /**
     * @see org.wewi.medimg.image.Image#getByteColor(int)
     */
    public byte getByteColor(int pos) {
        return image.getByteColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.Image#getShortColor(int)
     */
    public short getShortColor(int pos) {
        return image.getShortColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.Image#getLongColor(int)
     */
    public long getLongColor(int pos) {
        return image.getLongColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.Image#getFloatColor(int)
     */
    public float getFloatColor(int pos) {
        return image.getFloatColor(pos);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(byte)
     */
    public void fill(byte color) {
        image.fill(color);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(short)
     */
    public void fill(short color) {
        image.fill(color);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(int)
     */
    public void fill(int color) {
        image.fill(color);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(long)
     */
    public void fill(long color) {
        image.fill(color);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(float)
     */
    public void fill(float color) {
        image.fill(color);
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(double)
     */
    public void fill(double color) {
        image.fill(color);
    }

}
