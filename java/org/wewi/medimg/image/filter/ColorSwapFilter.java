/**
 * Created on 11.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
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
