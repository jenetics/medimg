/**
 * Created on 20.11.2002 18:31:41
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class KernelMaskSum {
    private Image image;
    private Kernel kernel;
    private int slice;
    

    /**
     * Constructor for KernelMaskSum.
     */
    public KernelMaskSum(Image image, int slice, Kernel kernel) {
        super();
        this.image = image;
        this.kernel = kernel;
        this.slice = slice;
    }
    
    public double getKernelMaskSum(int i, int j) {
        double kernelSum = 0;
        for (int k = -kernel.getMargin(), n = kernel.getMargin(); k <= n; k++) {
            for (int l = -kernel.getMargin(), m = kernel.getMargin(); l <= m; l++) {
                kernelSum += kernel.getValue(k, l) * image.getColor(i+k, j+l, slice);       
            } 
        } 
           
        return kernelSum/(double)kernel.getDivisor();    
    }

}
