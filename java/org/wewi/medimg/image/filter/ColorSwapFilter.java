/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public class ColorSwapFilter extends ImageFilter {

	/**
	 * Constructor for ColorSwapFilter.
	 * @param image
	 */
	public ColorSwapFilter(Image image) {
		super(image);
	}

	/**
	 * Constructor for ColorSwapFilter.
	 * @param component
	 */
	public ColorSwapFilter(ImageFilter component) {
		super(component);
	}
    
    public void filter() {
        super.filter();    
        
    }

}
