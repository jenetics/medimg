/**
 * Created on 24.10.2002 17:26:21
 *
 */
package org.wewi.medimg.image.filter.patterntest;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageFilter {

	/**
	 * Constructor for ImageFilter.
	 */
	public ImageFilter() {
		super();
	}
    
    public abstract void filter(Image source, Image target);

}
