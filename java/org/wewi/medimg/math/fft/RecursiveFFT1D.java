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
 * @author Franz Wilhelmst�tter
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
     * Aus <em>Introduction to Algorithms; second edition; Seite 835.</em>
     * 
     * L�nge <code>data.length</code> mu� eine Potenz von 2 sein!
     * 
     * @see org.wewi.medimg.math.fft.DFT1D#transform(Complex[])
     */
    public void transform(Complex[] data) throws IllegalArgumentException {
        transform(data, +1);
    }
    
    /**
     * @see org.wewi.medimg.math.fft.DFT1D#transformInverse(Complex[])
     */
    public void transformInverse(Complex[] data) throws IllegalArgumentException {
        transform(data, -1);        
    }    
    
    private void transform(Complex[] data, double dir) {
        if (!isPowerOfTwo(data.length)) {
            throw new IllegalArgumentException("The length of the given array is not a power of two: " +
                                                "data.length = " + data.length + " != 2^n"); 
        }
        
        final int N = data.length;
        Complex[] ffta = recursiveTransform(data, dir);
        
        //Skalieren der Daten
        final double M = 1d/(Math.pow((double)N, (1d - (dir*alpha))/2d));
        for (int i = 0; i < N; i++) {
            data[i] = MathUtil.mult(M, ffta[i]);    
        }        
                
    }
    
    private Complex[] recursiveTransform(Complex[] data, double dir) {
        final int N = data.length;
        if (N == 1) {
            return data;    
        }
        
        Complex[] a0 = new Complex[N/2];
        Complex[] a1 = new Complex[N/2];
        for (int i = 0, n = N/2; i < n; i++) {
            a0[i] = data[i*2];
            a1[i] = data[i*2+1];    
        }
        
        Complex[] y0 = recursiveTransform(a0, dir);
        Complex[] y1 = recursiveTransform(a1, dir);
        
        Complex[] y = new Complex[N];
        Complex W = Complex.ONE;

        final Complex Wn = MathUtil.exp(new Complex(0, dir*2*Math.PI*beta/(double)N));
        
        for (int k = 0, n = N/2; k < n; k++) {
            y[k] = y0[k].add(W.mult(y1[k]));
            y[k+n] = y0[k].sub(W.mult(y1[k]));
            
            W = W.mult(Wn);
        }            
        
        return y;
    }    

}
