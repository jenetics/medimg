/**
 * RecursiveFFT1D.java
 * 
 * Created on 17.12.2002, 10:19:32
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class RecursiveFFT1D extends DFT implements DFT1D {

    /**
     * Constructor for RecursiveFFT1D.
     */
    public RecursiveFFT1D() {
        super();
    }
    
    public RecursiveFFT1D(double alpha, double beta) {
        super(alpha, beta);
    }

    /**
     * Aus <it>Introduction to Algorithms; second edition; Seite 835.</it>
     * 
     * LÃ¤nge <code>a.length</code> muß eine Potenz von 2 sein!
     * 
     * @see org.wewi.medimg.math.fft.DFT1D#dft(Complex[])
     */
    public void transform(Complex[] a) throws IllegalArgumentException {
        if (!isPowerOfTwo(a.length)) {
            throw new IllegalArgumentException("The length of the given array is not a power of two: " +
                                            "a.length = " + a.length + " != 2^n"); 
        }
        
        //Complex[] ffta = rfft(a, +1);
        //System.arraycopy(ffta, 0, a, 0, ffta.length);        
        
        final int dir = +1;
        final int N = a.length;
        Complex[] ffta = rfft(a, dir);
        
        final double M = 1d/(Math.pow((double)N, (1d - (dir*alpha))/2d));
        for (int i = 0; i < N; i++) {
            a[i] = MathUtil.mult(M, ffta[i]);    
        }        
    }
    
    private Complex[] rfft(Complex[] a, double dir) {
        int N = a.length;
        if (N == 1) {
            return a;    
        }
        
        Complex[] a0 = new Complex[N/2];
        Complex[] a1 = new Complex[N/2];
        for (int i = 0, n = N/2; i < n; i++) {
            a0[i] = a[i*2];
            a1[i] = a[i*2+1];    
        }
        
        Complex[] y0 = rfft(a0, dir);
        Complex[] y1 = rfft(a1, dir);
        
        Complex[] y = new Complex[N];
        Complex W = new Complex(1);

        final Complex Wn = MathUtil.exp(new Complex(0, dir*2*Math.PI*beta/(double)N));
        
        for (int k = 0, n = N/2; k < n; k++) {
            y[k] = y0[k].add(W.mult(y1[k]));
            y[k+n] = y0[k].sub(W.mult(y1[k]));
            
            W = W.mult(Wn);
        }            
        
        return y;
    }    

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#transform(Complex[])
     */
    public void transformInverse(Complex[] a) {
        final int dir = -1;
        final int N = a.length;
        Complex[] ffta = rfft(a, dir);
        
        final double M = 1d/(Math.pow((double)N, (1d - (dir*alpha))/2d));
        for (int i = 0; i < N; i++) {
            a[i] = MathUtil.mult(M, ffta[i]);    
        }        
    }

}
