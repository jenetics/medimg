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
    
    private void trans(Complex[] data, double dir) {
        final int N = data.length;
        final double M = 1d/(Math.pow((double)N, (1d - (dir*alpha))/2d));
        final Complex Wn = MathUtil.exp(new Complex(0, dir*2*Math.PI*beta/(double)N));
        
        Complex[] result = new Complex[N];                                                                 
                                     
        for (int n = 0; n < N; n++) {
            Complex z = new Complex(0, 0);
            for (int k = 0; k < N; k++) {
                z = z.add(MathUtil.mult(data[k], MathUtil.pow(Wn, new Complex(k*n))));      
            } 
            
            result[n] = MathUtil.mult(M, z); 
        }
        
        System.arraycopy(result, 0, data, 0, N);        
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
        trans(data, +1);
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
    public void transformInverse(Complex[] a) {
        trans(a, -1);
    }

}
