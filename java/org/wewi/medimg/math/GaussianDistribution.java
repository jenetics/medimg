/*
 * GaussianDistribution.java
 *
 * Created on 11. April 2002, 10:23
 */

package org.wewi.medimg.math;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class GaussianDistribution implements DistributionFunction {
    private final double meanValue;
    private final double variance;
    private final double sqrVariance;
    
    /** Creates a new instance of GaussianDistribution */
    public GaussianDistribution(double meanValue, double variance) {
        this.meanValue = meanValue;
        this.variance = variance;
        sqrVariance = variance*variance;
    }
    
    public double[] eval(double[] arg) {
        double[] r = new double[1];
        r[0] = eval(arg[0]);
        return r;
    }
    
    public double eval(double x) {
        return (1.0d/(Math.sqrt(2d*Math.PI*sqrVariance))*
                Math.exp(-Math.pow(x-meanValue,2)/(2d*sqrVariance)));
    }
    
    public double getMeanValue() {
        return meanValue;
    }
    
    public double getVariance() {
        return variance;
    }
}
