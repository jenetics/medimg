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
public final class IterativeFFT1D extends DFT implements DFT1D {

    /**
     * Constructor for IterativeFFT1D.
     */
    public IterativeFFT1D() {
        super();
    }

    public IterativeFFT1D(double alpha, double beta) {
        super(alpha, beta);
    }

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#transform(Complex[])
     */
    public void transform(Complex[] data) {
        transform(data, +1);
    }

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#transformInverse(Complex[])
     */
    public void transformInverse(Complex[] data) {
        transform(data, -1);
    }

    private void transform(Complex[] data, double dir) {
        if (!isPowerOfTwo(data.length)) {
            throw new IllegalArgumentException("The length of the given array is not a power of two: " +
                                                "a.length = " + data.length + " != 2^n");
        }        
        
        final int N = data.length;
        final int LD_N = (int) Math.rint(MathUtil.log2(N));

        bitReverse(data);       

        int m = 0;
        Complex W, Wm, t, u;
        for (int s = 1; s <= LD_N; s++) {
            m = (int) Math.rint(MathUtil.pow(2, s));
            Wm =
                MathUtil.exp(
                    new Complex(0, dir * 2 * Math.PI * beta / (double) m));

            for (int k = 0; k < N; k += m) {
                W = Complex.ONE;

                for (int j = 0, n = m / 2; j < n; j++) {
                    t = W.mult(data[k + j + n]);
                    u = data[k + j];
                    data[k + j] = u.add(t);
                    data[k + j + n] = u.sub(t);
                    W = W.mult(Wm);
                }

            }
        }
        
        
        //Skalieren der Daten
        final double M = 1d / (Math.pow((double) N, (1d - (dir * alpha)) / 2d));
        for (int i = 0; i < N; i++) {
            data[i] = MathUtil.mult(M, data[i]);
        }        

    }

    private void bitReverse(Complex[] data) {
        /* This is the Goldrader bit-reversal algorithm */
        int j = 0;
        int n = data.length;

        for (int i = 0; i < n - 1; i++) {
            int k = n / 2;

            if (i < j) {
                Complex tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
            }

            while (k <= j) {
                j = j - k;
                k = k / 2;
            }

            j += k;
        }
    }

}
