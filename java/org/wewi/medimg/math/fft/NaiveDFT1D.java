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

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#dft(Complex[])
     */
    public void transform(Complex[] a) {
        final int N = a.length;
        final Complex Wn = MathUtil.exp(new Complex(0, 2*Math.PI/(double)N));
        
        Complex[] result = new Complex[N];                             
        final double MULT = 1d/Math.sqrt(N);                                     
                                     
        for (int n = 0; n < N; n++) {
            Complex z = new Complex(0, 0);
            for (int k = 0; k < N; k++) {
                z = z.add(MathUtil.mult(a[k], MathUtil.pow(Wn, new Complex(k*n))));      
            }
            result[n] = MathUtil.mult(MULT, z);    
        }
        
        System.arraycopy(result, 0, a, 0, N);       
    }

}
