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
public interface Image extends ImageGeometry, ImageAccess, Nullable, Cloneable, Mutable {
       
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
     * @param pos Colorposition; <code>0 <= pos < getNVoxels()</code>.
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
     * A convinient method for getting the number
     * of voxels of the image.
     * 
     * @return the number of voxels the image contains.
     */
    public int getNVoxels();
    
    /**
     * Returns the coresponding VoxelIterator of this image.
     * 
     * @return VoxelIterator of this image
     */
    public VoxelIterator getVoxelIterator();    
    
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

