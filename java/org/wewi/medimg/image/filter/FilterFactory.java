/**
 * Created on 22.11.2002 11:53:37
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class FilterFactory {

    /**
     * Constructor for FilterFactory.
     */
    private FilterFactory() {
        super();
    }
    
    public static ImageFilter createGaussianFilter(Image image, int dim, double stddev) {
        return new ConvolutionFilter(image, new GaussianKernel(dim, stddev));    
    }

}
