/*
 * MixtureModelDistribution.java
 *
 * Created on 10. Mai 2002, 21:11
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.math.DistributionFunction;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MixtureModelDistribution implements DistributionFunction {
    private DistributionFunction[] distributions;
    private double[] weights;
    private int length;

    public MixtureModelDistribution(DistributionFunction[] df, double[] pi) {
        distributions = df;
        length = pi.length;
        weights = new double[length];
        System.arraycopy(pi, 0, weights, 0, length);
    }
    
    public double eval(double x) {
        double result = 0;
        for (int i = 0; i < length; i++) {
            result += weights[i]*distributions[i].eval(x);
        }
        return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Mixture Model Distribution:\n");
        for (int i = 0; i < length; i++) {
            buffer.append("Weight: ").append(weights[i]).append(" ");
            buffer.append(distributions[i]);
        }
        return buffer.toString();
    }
}
