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
 * @version 0.1
 * 
 * @see org.wewi.medimg.image.ColorConversion
 * @see org.wewi.medimg.image.VoxelIterator
 * @see org.wewi.medimg.image.Dimension
 * 
 */
public interface Image extends ImageGeometry, ImageAccess, Nullable, Cloneable, Mutable {   
    
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

