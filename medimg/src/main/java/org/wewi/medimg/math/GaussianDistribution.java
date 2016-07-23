/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

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
