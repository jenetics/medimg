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
 * IterativeFFT1D.java
 * 
 * Created on 17.12.2002, 10:20:20
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.util.Timer;

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
        final int LD_N = (int) Math.rint(MathUtil.log2(N));

        bitReversal(data);       

        int m = 0;
        Complex W, Wm, t, u;
        for (int s = 1; s <= LD_N; s++) {
            m = (int) Math.rint(MathUtil.pow(2, s));
            Wm =
                MathUtil.exp(
                    new Complex(0, dir * 2 * Math.PI * b / (double) m));

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
        final double M = 1d / (Math.pow((double) N, (1d - (dir * a)) / 2d));
        for (int i = 0; i < N; i++) {
            data[i] = MathUtil.mult(M, data[i]);
        }        

    }

    private void bitReversal(Complex[] data) {
        //This is the Goldrader bit-reversal algorithm
        int j = 0;
        final int N = data.length;

        for (int i = 0; i < N - 1; i++) {
            int k = N / 2;

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
    
    
    
    /**
     * Für die Performancemessung
     */
    public static void main(String[] args) {
        final int MAX = 18;
        final int SIZE = (int)Math.rint(MathUtil.pow(2, MAX));
        
        Complex[] data = new Complex[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = new Complex(Math.random()*12, Math.random()*50);    
        }
        
        Timer timer = new Timer("FFT");
        //DFT1D dft = new RecursiveFFT1D();
        DFT1D dft = new IterativeFFT1D();
        timer.start();
        dft.transform(data);
        timer.stop();
        timer.print();
            
    }

}













