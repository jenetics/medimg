/**
 * Created on 14.11.2002 08:15:59
 *
 */
package org.wewi.medimg.image.filter;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class GaussianKernel extends Kernel {
    private double stddev;

    public GaussianKernel(int dim, double stddev) {
        super();
        this.dim = dim;
        margin = dim/2;
        bias = 0;
        
        raw = new int[dim*dim];
        
        //Bestimmen des Divisors
        divisor = (int)Math.round(1d/gaussian(margin, margin, stddev));
        
        int value = 0;
        for (int i = -margin; i <= margin; i++) {
            for (int j = -margin; j <= margin; j++) {
                value = (int)Math.round(gaussian(i , j, stddev)*(double)divisor);
                setValue(i, j, value);        
            }    
        }
    }
    
    private double gaussian(int x, int y, double stddev) {
        return 1/(2*Math.PI*stddev*stddev) *
                Math.exp(-(double)(x*x+y*y)/(2*stddev*stddev));    
    }

}
