/**
 * Math.java
 *
 * Created on 25. Juni 2002, 20:54
 */

package org.wewi.medimg.math;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class MathUtil {
    
    /** Creates a new instance of Math */
    private MathUtil() {
    }
    
    public static double sqr(double x) {
        return x*x;
    }
    
    public static int sqr(int x) {
        return x*x;
    }
    
    public static long sqr(long x) {
        return x*x;
    }            
    
    public static double pow(double x, int n) {
        double value = 1.0;

        if(n < 0) {
            x = 1.0/x;
            n = -n;
        }

        /* 
         * repeated squaring method 
         * returns 0.0^0 = 1.0, so continuous in x
         */
        do {
            if((n & 1) != 0) {
                value *= x;  /* for n odd */
            }
            n >>= 1;
            x *= x;
        } while (n != 0);

        return value;
    }
    
    
    public static void normalize(double[] array) {
        double value = 0;
        for (int i = 0, n = array.length; i < n; i++) {
            value += array[i];
        }
        
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] /= value;    
        }            
    }
    
}
