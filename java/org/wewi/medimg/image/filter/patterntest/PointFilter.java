/**
 * Created on 23.10.2002 21:55:30
 *
 */
package org.wewi.medimg.image.filter.patterntest;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PointFilter extends ImageFilter {
    private UnaryFunction function;

	/**
	 * Constructor for PointOperation.
	 */
	public PointFilter(UnaryFunction function) {
		super();
        this.function = function;
	}

	/**
	 * @see org.wewi.medimg.image.filter.ImageOperation#operate(Image, Image)
	 */
	public void filter(Image source, Image target) {
        for (int i = 0, n = source.getNVoxels(); i < n; i++) {
            target.setColor(i, function.execute(source.getColor(i)));    
        }
	}

}
