/**
 * Image.java
 *
 * Created on 18. Jänner 2002, 17:20
 *
 * 
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Mutable;
import org.wewi.medimg.util.Nullable;


/**
 * This interface represents the Image, on which the implemented algorithms
 * work. This image represents a 3d image, because the major intent of
 * image is the use in medical image processing. (An 2d image can be handled
 * as a special case of an 3d image.) 
 * For simplicity, the algorithms only handles greyscale images, so far.
 * But the images can also displayed as rgb images if the propper
 * ColorConversion is used.
 *
 *
 * @author Franz Wilhelmstötter
 * @version 0.2
 * 
 * @see org.wewi.medimg.image.ColorConversion
 * @see org.wewi.medimg.image.VoxelIterator
 * @see org.wewi.medimg.image.Dimension
 * 
 */
public interface Image extends Nullable, Cloneable, Mutable {
       
    /**
     * Sets the specific color at the position (x, y, z)
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @param color color to be set
     */
    public void setColor(int x, int y, int z, int color);
      
    /**
     * Sets the specific color at the specific position pos.
     * This method doubles the color getter, but if you not
     * interested on the color coordinates, this getter gives
     * you a faster access.
     * 
     * @param pos colorposition.
     * @param color color to be set.
     */
    public void setColor(int pos, int color);
    
    /**
     * Resets, respectively initializes, the image to the specified color.
     * 
     * @param color the reset color.
     */
    public void resetColor(int color);
    
    /**
     * Gets the color at the specified position.
     * 
     * @param pos colorposition.
     * @return the greyvalue at the position pos
     */
    public int getColor(int pos);
    
    /**
     * Gets the greyvalue at the coordinates (x, y, z)
     * 
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @param z z-coordinate.
     * @return the greyvalue at the position (x, y, z).
     */
    public int getColor(int x, int y, int z);      
    
    /**
     * Returns the minimal greyvalue of the current image.
     * 
     * @return the minimal greyvalue.
     * 
     * @todo This method should be removed. The mincolor determination
     *       can be done by an analyse function (class).
     */
    public int getMinColor();
    
    /**
     * Returns the maximal greyvalue of the current image.
     * 
     * @return the maximal greyvalue.
     * 
     * @todo This method should be removed. The maxcolor determination 
     *        can be done by an analyse function (class).
     */
    public int getMaxColor();
    
    /**
     * Returns the maximal valid x-coordinate.
     * 
     * @return the maximal valid x-cooridnate.
     */
    public int getMaxX();
    
    /**
     * Returns the maximal valid y-coordinate.
     * 
     * @return the maximal valid y-cooridnate.
     */    
    public int getMaxY();
    
    /**
     * Returns the maximal valid z-coordinate.
     * 
     * @return the maximal valid z-cooridnate.
     */    
    public int getMaxZ();
    
    /**
     * Returns the minimal valid x-coordinate.
     * 
     * @return the minimal valid x-cooridnate.
     */    
    public int getMinX();
    
    /**
     * Returns the minimal valid y-coordinate.
     * 
     * @return the minimal valid y-cooridnate.
     */     
    public int getMinY();
    
    /**
     * Returns the minimal valid z-coordinate.
     * 
     * @return the minimal valid z-cooridnate.
     */     
    public int getMinZ();
    
    /**
     * A convinient method for getting the number
     * of voxels of the image.
     * 
     * @return the number of voxels the image contains.
     */
    public int getNVoxels();
    
    /**
     * This method calculates the voxel position at
     * the coordinates (x, y, z). (For all image voxels holds:
     * <code>getColor(x, y, z) == getColor(getPosition(x, y,z))</code>.)
     * 
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @param z z-coordinate.
     * @return the coresponnding voxel position.
     */
    public int getPosition(int x, int y, int z);
    
    /**
     * This method calculates the voxel coordinates at
     * the specified voxel position. Every method invocation
     * creates a new int[] array. Should only used for a view 
     * coordinate calculations.
     * 
     * @param pos voxel position
     * @return the coordinates of the position pos
     */
    public int[] getCoordinates(int pos);   
    
    /**
     * Calculates the voxel coordinates at the specified position.
     * 
     * @param pos voxel position
     * @param coordinate copies the voxel coordinates into this
     *         int[] array.
     */
    public void getCoordinates(int pos, int[] coordinate);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n12 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 12. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */
    public void getNeighbor3D12Positions(int pos, int[] n12);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n6 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 6. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */    
    public void getNeighbor3D6Positions(int pos, int[] n6);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n18 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 18. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */    
    public void getNeighbor3D18Positions(int pos, int[] n18);
    
    /**
     * Returns the coresponding VoxelIterator of this image.
     * 
     * @return VoxelIterator of this image
     */
    public VoxelIterator getVoxelIterator();
    
    /**
     * Returns the ColorRange of this image.
     * 
     * @return ColorRange of this image.
     * 
     * @todo This method should be removed. The color range determination
     *       can be done by an analyse function (class).
     */
    public ColorRange getColorRange();
    
    /**
     * Returns the image dimension
     * 
     * @return image dimension
     */
    public Dimension getDimension();
    
    /**
     * Returns the ImageHeader
     * 
     * @return ImageHeader
     */
    public ImageHeader getHeader();
    
    /**
     * Returns the ColorConversion, specified for this image.
     * 
     * @return ColorConversion
     */
    public ColorConversion getColorConversion();
    
    /**
     * Setting the ColorConverion of this image. The ColorConversion is
     * primary used for displaying color images.
     * 
     * @param cc ColorConversion to be set.
     */
    public void setColorConversion(ColorConversion cc);     
    
    /**
     * For many algorithms it is necessary to create an identical
     * copy of the current image. Therefore every implementation of the
     * Image interface must implement the clone method.
     * 
     * @return An identical copy of the image.
     */
    public Object clone();
    
}

