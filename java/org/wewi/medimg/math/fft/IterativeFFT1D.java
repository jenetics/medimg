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
    public void transform(Complex[] a) throws IllegalArgumentException {
        if (!isPowerOfTwo(a.length)) {
            throw new IllegalArgumentException(
                "The length of the given array is not a power of two: "
                    + "a.length = "
                    + a.length
                    + " != 2^n");
        }

        final int dir = +1;
        final int N = a.length;
        Complex[] ffta = trans(a, dir);

        final double M = 1d / (Math.pow((double) N, (1d - (dir * alpha)) / 2d));
        for (int i = 0; i < N; i++) {
            a[i] = MathUtil.mult(M, ffta[i]);
        }
    }

    /**
     * @see org.wewi.medimg.math.fft.DFT1D#transformInverse(Complex[])
     */
    public void transformInverse(Complex[] a) throws IllegalArgumentException {
        if (!isPowerOfTwo(a.length)) {
            throw new IllegalArgumentException(
                "The length of the given array is not a power of two: "
                    + "a.length = "
                    + a.length
                    + " != 2^n");
        }

        final int dir = -1;
        final int N = a.length;
        Complex[] ffta = trans(a, dir);

        final double M = 1d / (Math.pow((double) N, (1d - (dir * alpha)) / 2d));
        for (int i = 0; i < N; i++) {
            a[i] = MathUtil.mult(M, ffta[i]);
        }
    }

    private Complex[] trans(Complex[] a, double dir) {
        final int N = a.length;
        final int LD_N = (int) Math.rint(MathUtil.log2(N));

        //Complex[] A = new Complex[a.length];
        //bitReverseCopy(a, A, LD_N); 
        
        bitReverse(a);       

        int m = 0;
        Complex W = null;
        Complex Wm = null;
        Complex t = null;
        Complex u = null;
        for (int s = 1; s <= LD_N; s++) {
            m = (int) Math.rint(MathUtil.pow(2, s));
            Wm =
                MathUtil.exp(
                    new Complex(0, dir * 2 * Math.PI * beta / (double) m));

            for (int k = 0; k < N; k += m) {
                W = Complex.ONE;

                /*
                for (int j = 0, n = m/2; j < n; j++) {
                    t = W.mult(A[k + j + n]);
                    u = A[k + j];
                    A[k + j] = u.add(t);
                    A[k + j + n] = u.sub(t);
                    W = W.mult(Wm);
                }
                */

                for (int j = 0, n = m / 2; j < n; j++) {
                    t = W.mult(a[k + j + n]);
                    u = a[k + j];
                    a[k + j] = u.add(t);
                    a[k + j + n] = u.sub(t);
                    W = W.mult(Wm);
                }

            }
        }

        return a;
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
            A[i] = a[rev(i, bits)];
        }
    }

    private void bitReverse(Complex[] data) {
        /* This is the Goldrader bit-reversal algorithm */
        int i;
        int j = 0;
        int n = data.length;

        for (i = 0; i < n - 1; i++) {
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
