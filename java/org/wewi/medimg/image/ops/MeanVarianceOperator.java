/**
 * MeanVarianceOperator.java
 *
 * Created on 24. Jänner 2003, 09:11
 */

package org.wewi.medimg.image.ops;

import org.wewi.medimg.math.MathUtil;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class MeanVarianceOperator implements UnaryOperator {
    private int n = 0;
    private long sum = 0;
    private long sqrSum = 0;
    
    /** Creates a new instance of MeanVarianceOperator */
    public MeanVarianceOperator() {
    }
    
    public void process(int color) {
        n++;
        sum += color;
        sqrSum += MathUtil.sqr(color);
    }
    
    
    public double getMean() {
        return (double)sum/(double)n;
    }
    
    public double getVariance() {
        double mean = getMean();
        return ((double)sqrSum - 2*mean*sum + n*MathUtil.sqr(mean))/(double)(n-1);
    }
}
