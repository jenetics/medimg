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
    
    public boolean isPowerOfTwo(int n) {
        double ldn = MathUtil.log2(n);
        
        return true;    
    }    
         
    
    
    public static Complex[][] naiveFFT(Complex[][] a) {
        DFT1D dft = new NaiveDFT1D();
        
        Complex[][] A = new Complex[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                A[i][j] = a[i][j];    
            }    
        }
        
        //Zeilentransformation
        for (int i = 0; i < a.length; i++) {
            dft.transform(A[i]);   
        }
        
        
        //Spaltentransformation
        Complex[] col = new Complex[A.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                col[j] = A[j][i];    
            }    
            
            dft.transform(col);
            
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
