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
 * CrossCorrelation.java
 * 
 * Created on 30.12.2002, 14:26:20
 *
 */
package org.wewi.medimg.image.ops;


import org.wewi.medimg.util.AccumulatorArray;


/**
 * @author Werner Weiser
 * 
 * @since 0.1
 */
public class CrossCorrelation {
    private AccumulatorArray accu;
    private final double N;

    /**
     * Constructor for MutualInformation.
     */
    public CrossCorrelation(AccumulatorArray accu) {
        if (accu == null) {
            throw new IllegalArgumentException("AccumulatorArray must not be null!");    
        }
        
        this.accu = accu;
        N = accu.elementSum();
    }
    
    /**
     * 
     *               --            
     *               \                                   
     *               /   (x - myX) *  (y - myY)
     *               --                            
     *             x in X          

     * CC(X, Y) = ---------------------------------------------  
     *            /   --                 --              \  1/2
     *            |   \                  \                |      
     *            |   /   (x - myX)^2 *  /  (y - myY)^2   | 
     *            |   --                 --               | 
     *            |  x in X               y in Y          |
     *             \                                     /
     * 
     */
    public double getCrossCorrelation() {
        if (N <= 0) {
            return 0;    
        }
        
        double[] px = new double[accu.getCols()];
        double[] py = new double[accu.getRows()];
        calculateProbabilities(px, py);
        double myX = 0;
        double myY = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            myX += px[i] * i;
        }
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            myY += py[i] * i;
        }
        
        double cc = 0, aij = 0, cc1 = 0, cc2 = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                aij = accu.getValue(j, i);   
                if (aij == 0) {
                    continue;    
                } 
                cc += aij * (i - myX) * (j - myY); 
                cc1 += aij * Math.pow((i - myX), 2);
                cc2 += aij * Math.pow((j - myY), 2);    
            }    
        }
        cc1 = Math.sqrt(cc1 * cc2);
        return cc/cc1;        
    }
    
    private void calculateProbabilities(double[] x, double[] y) {
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


    






