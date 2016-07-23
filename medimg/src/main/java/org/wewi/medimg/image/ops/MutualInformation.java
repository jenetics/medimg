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
    
    public AccumulatorArray accu;
    private final double N;

    /**
     * Constructor for MutualInformation.
     */
    public MutualInformation(AccumulatorArray accu) {
        if (accu == null) {
            throw new IllegalArgumentException("AccumulatorArray must not be null!");    
        }
        
        this.accu = accu;
        N = accu.elementSum();
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
       
        if (N <= 0) {
            return 0;    
        }
        
        double[] px = new double[accu.getCols()];
        double[] py = new double[accu.getRows()];
        calculateProbabilities(px, py);
        
        double mi = 0, aij = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                aij = accu.getValue(j, i);   
                if (aij == 0) {
                    continue;    
                } 
                mi += (aij) * MathUtil.log2((aij/N)/(px[i]*py[j]));    
            }    
        }
        //damit aij/N nicht in jedem Durchlauf berechnet werden muß
        return mi/N;        
    }
    
    public void calculateProbabilities(double[] x, double[] y) {
    	long pxsum = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            pxsum = 0;
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                pxsum += accu.getValue(j, i);
            }
            x[i] = (double)pxsum / N;
        }
        
        long pysum = 0;
        for (int j = 0, m = accu.getRows(); j < m; j++) {
            pysum = 0;
            for (int i = 0, n = accu.getCols(); i < n; i++) {
                pysum += accu.getValue(j, i);
            }
            y[j] = (double)pysum / N;
        }
    }
    

}





