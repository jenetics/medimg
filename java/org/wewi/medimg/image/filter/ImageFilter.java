/*
 * ImageFilter.java
 *
 * Created on 25. Jänner 2002, 17:32
 */

package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageFilter {
    protected Image image = null;
    private ImageFilter component = null;
    
    public ImageFilter(Image image) {
        this.image = image;
    }
    
    public ImageFilter(ImageFilter component) {
        this.component = component;
        image = component.getImage();
    }
    
    public void filter() {
        if (component != null) {
            component.filter();
        }
    }
    
    public Image getImage() {
        if (image != null) {
            return image;
        } else {
            return component.getImage();
        }
    }
}

