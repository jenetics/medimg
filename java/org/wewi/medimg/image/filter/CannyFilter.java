/**
 * Created on 22.11.2002 10:34:54
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class CannyFilter extends ImageFilter {

	/**
	 * Constructor for CannyFilter.
	 * @param image
	 */
	public CannyFilter(Image image) {
		super(image);
	}

	/**
	 * Constructor for CannyFilter.
	 * @param component
	 */
	public CannyFilter(ImageFilter component) {
		super(component);
	}
    
    protected void componentFilter() {
        
        Image cannyImage = (Image)image.clone();
        
        //1. 
        
        
        //Do the canny filtering    
    }

}
