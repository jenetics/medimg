/* 
 * Image.java, Created on 18. Jänner 2002, 17:20
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
    
    //Image access methods
    
    public void setColor(int pos, byte color);
    
    public void setColor(int pos, short color);
    
    public void setColor(int pos, int color);
    
    public void setColor(int pos, long color);
    
    public void setColor(int pos, float color);
    
    public void setColor(int pos, double color);
    
    public byte getByteColor(int pos);
    
    public short getShortColor(int pos);
    
    public int getIntColor(int pos);
    
    public long getLongColor(int pos);
    
    public float getFloatColor(int pos);
    
    public double getDoubleColor(int pos);
    
    public void fill(byte color);
    
    public void fill(short color);
    
    public void fill(int color);
    
    public void fill(long color);
    
    public void fill(float color);
    
    public void fill(double color);
    
    public PixelIterator iterator();
    
    /**
     * For many algorithms it is necessary to create an identical
     * copy of the current image. Therefore every implementation of the
     * Image interface must implement the clone method.
     * 
     * @return An identical copy of the image.
     */
    public Object clone();
    
}

