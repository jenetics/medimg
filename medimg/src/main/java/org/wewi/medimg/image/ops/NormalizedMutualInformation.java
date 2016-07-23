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

package org.wewi.medimg.image.ops;

import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author werner weiser
 *
 */
public class NormalizedMutualInformation extends MutualInformation {

	private AccumulatorArray accu;
	private final double N;
	
	/**
	 * Constructor for NormalizedMutualInformation.
	 * @param accu
	 */
	public NormalizedMutualInformation(AccumulatorArray accu) {
		super(accu);
		this.accu = super.accu;
        N = accu.elementSum();
	}
	
    /**
     * Scheme 1:
     * 
     *               --                  --
     *               \                   \                      
     *               /   p(x) ld (p(x)) +  /     p(y) * ld (p(y))
     *               --                  --                
     *             x in X                y in Y

     * NMI(X, Y) = --------------------------------------------- + 3.0 
     *               --       --
     *               \        \                      p(x and y)
     *               /        /     p(x and y) * ld ------------
     *               --       --                     p(x) * p(y)
     *             x in X   y in Y
     * 
     */
    public double getNormalizedMutualInformation() {
        if (accu == null) {
            return 0;    
        }
               
        if (N <= 0) {
            return 0;    
        }
        
        double[] px = new double[accu.getCols()];
        double[] py = new double[accu.getRows()];
        super.calculateProbabilities(px, py);
        
        double xSum = 0.0;
        double ySum = 0.0;
		for (int i = 0, n = accu.getCols(); i < n; i++) {
			if (px[i] == 0) {
				continue;
			}
		    xSum += px[i] * MathUtil.log2(px[i]);
		}
        for (int j = 0, m = accu.getRows(); j < m; j++) {
        	if (py[j] == 0) {
				continue;
			}
        	ySum += py[j] * MathUtil.log2(py[j]);	
        }
        
       
        
        
        //double nmi = (xSum + ySum) / super.getMutualInformation();        
		//return nmi + 3.0;
		//scheme 2
		//double nmi = (2 * super.getMutualInformation()) / (xSum + ySum); 
        //return nmi;  
        //scheme 3
		//double nmi = ((2 * super.getMutualInformation()) / (xSum + ySum)) - 2; 
        //return 1/nmi; 
        double hxy = 0, aij = 0;
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            for (int j = 0, m = accu.getRows(); j < m; j++) {
                aij = accu.getValue(j, i);   
                if (aij == 0) {
                    continue;    
                } 
                hxy += (aij/N) * MathUtil.log2(aij/N);    
            }    
        }
        //scheme 4 
		//double nmi = hxy - super.getMutualInformation(); 
        //return nmi; 
        //scheme 5 
		double nmi = (xSum + ySum) / hxy; 
        return nmi - 1; 
    }	

}
