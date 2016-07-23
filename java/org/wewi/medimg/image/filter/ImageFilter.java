/* 
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

/**
 * ImageFilter.java
 *
 * Created on 25. Jänner 2002, 17:32
 */

package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * Baseclass for the image filtering tasks.
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageFilter {
    protected Image image = null;
    private ImageFilter component = null;
    
    /**
     * Constructs a new ImageFilter for the given image.
     * 
     * @param image to be filtered
     */
    public ImageFilter(Image image) {
        this.image = image;
    }
    
    /**
     * Constructs a new ImageFilter and concatinates it to the component
     * ImageFilter. The component ImageFilter will be performed first.
     * 
     * @param component ImageFilter will filter the image first.
     */
    public ImageFilter(ImageFilter component) {
        this.component = component;
        image = component.getImage();
    }
    
    /**
     * This method starts the filter process.
     */
    public final void filter() {
        if (component != null) {
            component.filter();
        }
        
        componentFilter();
    }
    
    /**
     * This abstract filter method performes the filter task of the inherited
     * classes.
     */
    protected abstract void componentFilter();
    
    /**
     * Returns the filtered image.
     * 
     * @return Image
     */
    public Image getImage() {
        if (image != null) {
            return image;
        } else {
            return component.getImage();
        }
    }
}

