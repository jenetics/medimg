/**
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
public final class GaussianDistribution implements DistributionFunction {
    private final double meanValue;
    private final double variance;
    
    public GaussianDistribution(double meanValue, double variance) {
        this.meanValue = meanValue;
        this.variance = variance;
    }
    

    public double eval(double x) {
        return (1.0d/(Math.sqrt(2d*Math.PI*variance))*
                Math.exp(-MathUtil.sqr(x-meanValue)/(2d*variance)));
    }
    
     
    public double getMeanValue() {
        return meanValue;
    }
    
    public double getVariance() {
        return variance;
    }
    
    public String toString() {
        return "Gaussian Distribution\n    Meanvalue: " + meanValue + ", Variance: " + variance;
    }
    
    /*
    public static void main(String[] args) {
        int MAX = 20000000;
        
        Timer timer = new Timer("Test");
        GaussianDistribution d = new GaussianDistribution(1.3, 6.5);
        timer.start();
        for (int i = 0; i < MAX; i++) {
            d.eval(i);
        }
        timer.stop();
        timer.print();
    }
    */
}
