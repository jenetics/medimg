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
 * NaiveDFT1D.java
 * 
 * Created on 17.12.2002, 10:16:25
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class NaiveDFT1D extends DFT implements DFT1D {

    /**
     * Constructor for NaiveDFT1D.
     */
    public NaiveDFT1D() {
        super();
    }
    
    public NaiveDFT1D(double alpha, double beta) {
        super(alpha, beta);    
    }
    
    /**
     * Fouriertransformation.
     * 
     * <code>
     *     F(n) = Sum[f(k)*exp[-2*PI*n*k/N], {k, 0, N-1}]
     * </code>
     * 
     * @see org.wewi.medimg.math.fft.DFT1D#dft(Complex[])
     */
    public void transform(Complex[] data) {
        transform(data, +1);
    }

    /**
     * Inverse Fouriertransformation
     * 
     * <code>
     *     f(n) = (1/N)*Sum[F(k)*exp[2*PI*n*k/N], {k, 0, N-1}]
     * </code>
     * 
     * @see org.wewi.medimg.math.fft.DFT1D#transformBackward(Complex[])
     */
    public void transformInverse(Complex[] data) {
        transform(data, -1);
    }    
    
    
    private void transform(Complex[] data, double dir) {
        final int N = data.length;
        final double M = 1d/(Math.pow((double)N, (1d - (dir*a))/2d));
        final Complex Wn = MathUtil.exp(new Complex(0, dir*2*Math.PI*b/(double)N));
        
        Complex[] result = new Complex[N];                                                                 
                                     
        for (int n = 0; n < N; n++) {
            Complex z = Complex.NULL;
            for (int k = 0; k < N; k++) {
                z = z.add(MathUtil.mult(data[k], MathUtil.pow(Wn, new Complex(k*n))));      
            } 
            
            result[n] = MathUtil.mult(M, z); 
        }
        
        System.arraycopy(result, 0, data, 0, N);        
    }

}
