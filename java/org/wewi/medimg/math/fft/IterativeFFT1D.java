/**
 * IterativeFFT1D.java
 * 
 * Created on 17.12.2002, 10:20:20
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class IterativeFFT1D implements DFT1D {

    /**
     * Constructor for IterativeFFT1D.
     */
    public IterativeFFT1D() {
        super();
    }

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#dft(Complex[])
     */
    public void dft(Complex[] a) {
        Complex[] ffta = iterativeFFT(a);
        System.arraycopy(ffta, 0, a, 0, a.length);
    }
    
    private int rev(int k, int bits) {
        int rev = 0;
        for (int i = rev = 0; i < bits; i++) {
            rev = (rev << 1) | (k & 1);
            k >>= 1;    
        }                                                                               
        
        return rev;    
    }
    
    private void bitReverseCopy(Complex[] a, Complex[] A, int bits) {
        for (int i = 0, n = a.length; i < n; i++) {
            A[rev(i, bits)] = a[i];       
        }    
    }
    
    private Complex[] iterativeFFT(Complex[] a) {        
        int n = a.length;
        int ldn = (int)MathUtil.log2(n);
        
        Complex[] A = new Complex[a.length];
        bitReverseCopy(a, A, ldn);        
        
        int m = 0;
        Complex W = null;
        Complex Wm = null;
        Complex t = null;
        Complex u = null;
        for (int s = 1; s <= ldn; s++) {
            m = (int)MathUtil.pow(2, s);
            Wm = MathUtil.exp(new Complex(0, 2*Math.PI/(double)m));
            
            for (int k = 0; k < n; k += m) {
                W = new Complex(1);
                
                for (int j = 0; j < m/2; j++) {
                    t = W.mult(A[k + j + m/2]);
                    u = A[k + j];
                    A[k +j] = u.add(t);
                    A[k + j + m/2] = u.sub(t);
                    W = W.mult(Wm);
                }        
            }
        }
         
        return A;    
    }    

}
