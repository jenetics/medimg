/**
 * MutualInformation.java
 * 
 * Created on 30.12.2002, 14:26:20
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutualInformation {
    
    private AccumulatorArray accu;

    /**
     * Constructor for MutualInformation.
     */
    public MutualInformation(AccumulatorArray accu) {
        if (accu == null) {
            throw new IllegalArgumentException("AccumulatorArray must not be null!");    
        }
        
        this.accu = accu;
    }
    
    /**
     *               --       --
     *               \        \                      p(x and y)
     *  MI(X, Y) =   /        /     p(x and y) * ld ------------
     *               --       --                     p(x) * p(y)
     *             x in X   y in Y
     * 
     */
    public double getMutualInformation() {
        if (accu == null) {
            return 0;    
        }
        
        final double N = accu.elementSum();
        
        if (N <= 0) {
            return 0;    
        }
        
        double[] px = new double[accu.getCols()];
        double[] py = new double[accu.getRows()];
        
        {//This block calculates the probabilities px[i] and py[i]
            long pxsum = 0;
            for (int i = 0, n = accu.getCols(); i < n; i++) {
                pxsum = 0;
                for (int j = 0, m = accu.getRows(); j < m; j++) {
                    pxsum += accu.getValue(j, i);
                }
                px[i] = (double)pxsum / N;
            }
            
            long pysum = 0;
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                pysum = 0;
                for (int i = 0, n = accu.getCols(); i < n; i++) {
                    pysum += accu.getValue(j, i);
                }
                py[j] = (double)pysum / N;
            }
        }        
        
        
        double mi = 0, aij = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                aij = accu.getValue(j, i);   
                if (aij == 0) {
                    continue;    
                }                
                 
                mi += aij * MathUtil.log2((aij/N)/(px[i]*py[j]));        
            }    
        }

        return mi/N;        
    }

}





