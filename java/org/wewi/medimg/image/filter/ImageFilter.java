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

