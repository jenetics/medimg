/**
 * DFT.java
 * 
 * Created on 14.12.2002, 15:28:34
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
public class DFT {

    /**
     * Constructor for DFT.
     */
    public DFT() {
        super();
    }
    
    

    /**
     * Fouriertransformation für komplexe 1D Daten. O(n^2)
     */
    public static Complex[] naiveDFT1D(Complex[] data) {
        final int N = data.length;
        final Complex Wn = MathUtil.exp(new Complex(0, 2*Math.PI/(double)N));
        
        Complex[] result = new Complex[N];                             
        final double MULT = 1d/Math.sqrt(N);                                     
                                     
        for (int n = 0; n < N; n++) {
            Complex z = new Complex(0, 0);
            for (int k = 0; k < N; k++) {
                z = z.add(MathUtil.mult(data[k], MathUtil.pow(Wn, new Complex(k*n))));      
            }
            result[n] = MathUtil.mult(MULT, z);    
        }
        
        return result;    
    }
     
    
    public static Complex[] recursiveFFT(Complex[] a) {
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
        
        Complex[] y0 = recursiveFFT(a0);
        Complex[] y1 = recursiveFFT(a1);
        
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            y[k] = y0[k].add(W.mult(y1[k]));
            y[k+(n/2)] = y0[k].sub(W.mult(y1[k]));
            W = W.mult(Wn);
        }            
        
        return y;
    }      
       
    
    private static final int rev(int k, int bits) {
        int rev = 0;
        for (int i = rev = 0; i < bits; i++) {
            rev = (rev << 1) | (k & 1);
            k >>= 1;    
        }                                                                               
        
        return rev;    
    }
    private static void bitReverseCopy(Complex[] a, Complex[] A, int bits) {
        for (int i = 0, n = a.length; i < n; i++) {
            A[rev(i, bits)] = a[i];       
        }    
    }
    public static Complex[] iterativeFFT(Complex[] a) {        
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
    
    
    public static Complex[][] naiveFFT(Complex[][] a) {
        Complex[][] A = new Complex[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                A[i][j] = a[i][j];    
            }    
        }
        
        //Zeilentransformation
        for (int i = 0; i < a.length; i++) {
            A[i] = naiveDFT1D(A[i]);   
        }
        
        
        //Spaltentransformation
        Complex[] col = new Complex[A.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                col[j] = A[j][i];    
            }    
            
            col = naiveDFT1D(col);
            
            for (int j = 0; j < a.length; j++) {
                A[j][i] = col[j];    
            }            
        }
        
        
        return A;    
    } 
    private static void scale(double s, Complex[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i]  = MathUtil.mult(s, a[i]);    
        }
    }
    
    
    
    public static void main(String[] args) {
        /*
        final int SIZE = (int)MathUtil.pow(2, 20);
        Complex[] data = new Complex[SIZE];
        Complex[] result;
        for (int i = 0; i < SIZE; i++) {
            data[i] = new Complex(Math.sin(i), Math.cos(i)+1);    
        }
        
        Timer t = new Timer("FFT");
        t.start();
        //naiveDFT1D(data);
        t.stop();
        t.print();
        
        t.start();
        recursiveFFT(data);
        t.stop();
        t.print();
        
        t.start();
        iterativeFFT(data);
        t.stop();
        t.print();                
        */
        
        
        Complex[][] data = new Complex[][]{{new Complex(1), new Complex(2)},
                                            {new Complex(2), new Complex(5)},
                                            {new Complex(0), new Complex(0)},
                                            {new Complex(0), new Complex(0)}};
        Complex[][] result;
        
        result = naiveFFT(data);
        
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j]);
                System.out.print("    ");    
            }    
            System.out.println();
        }
        
/*
        Complex[] data = new Complex[4];
        data[0] = new Complex(1, 0);
        data[1] = new Complex(4, 0);
        data[2] = new Complex(2, 0);
        data[3] = new Complex(6, 0);
        Complex[] result;
        
        result = naiveDFT1D(data);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);    
        }
        System.out.println();        
        
        result = recursiveFFT(data);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);    
        }
        System.out.println();
                
        result = iterativeFFT(data);        
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);    
        }
*/                


            
    }

}
