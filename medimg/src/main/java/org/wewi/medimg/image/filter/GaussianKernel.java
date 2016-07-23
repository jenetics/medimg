/* 
 * Created on 14.11.2002 08:15:59
 * 
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

package org.wewi.medimg.image.filter;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class GaussianKernel extends Kernel {
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
