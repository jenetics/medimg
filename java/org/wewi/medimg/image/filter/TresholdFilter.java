/**
 * Created on 22.10.2002 17:22:39
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class TresholdFilter extends ImageFilter {
    private int upperBound;
    private int lowerBound;

	/**
	 * Constructor for TresholdFilter.
	 * @param image
	 */
	public TresholdFilter(Image image, int lowerBound, int upperBound) {
		super(image);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
	}

	/**
	 * Constructor for TresholdFilter.
	 * @param component
	 */
	public TresholdFilter(ImageFilter component, int lowerBound, int upperBound) {
		super(component);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
	}

	/**
	 * @see org.wewi.medimg.image.filter.ImageFilter#filter()
	 */
	public void filter() {
		super.filter();
        
        int color = 0;
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            color = image.getColor(i);
            if (color < lowerBound) {
                image.setColor(i, 0);
                continue;    
            }
            if (color > upperBound) {
                image.setColor(i, 0);        
            }        
        }
	}

}
