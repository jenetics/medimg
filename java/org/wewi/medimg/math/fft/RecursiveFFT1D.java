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

    /**
     * Aus <it>Introduction to Algorithms; second edition; Seite 835.</it>
     * 
     * Länge <code>a.length</code> muß eine Potenz von 2 sein!
     * 
     * @see org.wewi.medimg.math.fft.DFT1D#dft(Complex[])
     */
    public void transform(Complex[] a) throws IllegalArgumentException {
        if (!isPowerOfTwo(a.length)) {
            throw new IllegalArgumentException("The length of the given array is not a power of two: " +
                                            "a.length = " + a.length + " != 2^n"); 
        }
        
        Complex[] ffta = rfft(a);
        System.arraycopy(ffta, 0, a, 0, ffta.length);        
    }
    
    private Complex[] rfft(Complex[] a) {
        int n = a.length;
        if (n == 1) {
            return a;    
        }
        
        Complex Wn = MathUtil.exp(new Complex(0, 2*Math.PI/(double)n));
        Complex W = new Complex(1);
        
        Complex[] a0 = new Complex[n/2];
        Complex[] a1 = new Complex[n/2];
        for (int i = 0, ii = n/2; i < ii; i++) {
            a0[i] = a[i*2];
            a1[i] = a[i*2+1];    
        }
        
        Complex[] y0 = rfft(a0);
        Complex[] y1 = rfft(a1);
        
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            y[k] = y0[k].add(W.mult(y1[k]));
            y[k+(n/2)] = y0[k].sub(W.mult(y1[k]));
            W = W.mult(Wn);
        }            
        
        return y;
    }    

}
