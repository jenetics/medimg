/**
 * Math.java
 *
 * Created on 25. Juni 2002, 20:54
 */

package org.wewi.medimg.math;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public final class MathUtil {

    /** Creates a new instance of Math */
    private MathUtil() {
    }
    
    /**
     * Modern Euclidian algorithm
     * 
     * @param u
     * @param v
     * @return greatest common divisor
     */
    public static int gcd(int u, int v) {
        int t = 0;
        if (u < v) {
            t = u; u = v; v = t;
        }
        while (v != 0) {
            t = u % v;
            u = v;
            v = t;
        }
        
        return u;
    }
    
    /**
     * Binary gcd algorithm
     * 
     * @param u
     * @param v
     * @return greates common divisor
     */
    /*
    public static int bgcd(int u, int v) {
        int t = 0;
        if (u < v) {
            t = u; u = v; v = t;
        }
        
        //Find power of 2
        int k = 0;
        while (((u & 1) == 1) || ((v & 1) == 1)) {
            ++k;
            u >>= 2;
            v >>= 2;
        }

        //Initialize
        if ((u & 1) == 1) {
            t = -v;
        } else {
            t = u;
        }
        
        
        //Half t
        while ((t & 1) == 1){
            t >>= 2;
        }
        
        
        //B4. Is t even?
        if ((t & 1) == 1) {
            //Yes -> halve t
            t >>= t;
        } else {
            //No -> B5. reset max(u, v)
            if (t > 0) {
                u = t;
            } else {
                v = -t;
            }
        }
        
        return u;        
    }
    */

    public static double sqr(double x) {
        return x * x;
    }
    
    public static float sqr(float x) {
        return x * x;    
    }

    public static int sqr(int x) {
        return x * x;
    }

    public static long sqr(long x) {
        return x * x;
    }

    public static double pow(double x, int n) {
        double value = 1.0;

        if (n < 0) {
            x = 1.0 / x;
            n = -n;
        }

        /* 
         * repeated squaring method 
         * returns 0.0^0 = 1.0, so continuous in x
         */
        do {
            if ((n & 1) != 0) {
                value *= x; /* for n odd */
            }
            n >>= 1;
            x *= x;
        } while (n != 0);

        return value;
    }

    public static int pow2(int n) {
        return (1 << n);
    }

    public static double log2(double x) {
        //return Math.log(x)*(1/Math.log(2));
        return Math.log(x) * 1.442695040888963407359924681;
    }

    public static double log10(double x) {
        //return Math.log(x)*(1/Math.log(10));
        return Math.log(x) * 0.434294481903251827651128918917;
    }

    public static long[] primeFactors(long number) {
        List v = new ArrayList();
        long divisor = 2L;
        long exponent;
        long root;
        if (number > 0) {
            if (number % divisor == 0) {
                exponent = 0;
                v.add(new Long(divisor));
                do {
                    exponent++;
                    number /= divisor;
                } while (number % divisor == 0);
                v.add(new Long(exponent));
            }
            divisor = 3L;
            root = (long) Math.sqrt(number) + 1;
            while (divisor <= root) {
                if (number % divisor == 0) {
                    exponent = 0;
                    v.add(new Long(divisor));
                    do {
                        exponent++;
                        number /= divisor;
                    } while (number % divisor == 0);
                    v.add(new Long(exponent));
                    root = (long) Math.sqrt(number) + 1L;
                }
                divisor += 2L;
            }
            if (number > 1) {
                v.add(new Long(number));
                v.add(new Long(1L));
            }
        }

        long[] result = new long[v.size()];
        for (int i = 0, n = v.size(); i < n; i++) {
            result[i] = ((Long)v.get(i)).longValue();    
        }
        return result;
    }

    // Series on the interval [0,1]
    private static final double ASINH_COEF[] =
        {
            -.12820039911738186343372127359268e+0,
            -.58811761189951767565211757138362e-1,
            .47274654322124815640725249756029e-2,
            -.49383631626536172101360174790273e-3,
            .58506207058557412287494835259321e-4,
            -.74669983289313681354755069217188e-5,
            .10011693583558199265966192015812e-5,
            -.13903543858708333608616472258886e-6,
            .19823169483172793547317360237148e-7,
            -.28847468417848843612747272800317e-8,
            .42672965467159937953457514995907e-9,
            -.63976084654366357868752632309681e-10,
            .96991686089064704147878293131179e-11,
            -.14844276972043770830246658365696e-11,
            .22903737939027447988040184378983e-12,
            -.35588395132732645159978942651310e-13,
            .55639694080056789953374539088554e-14,
            -.87462509599624678045666593520162e-15,
            .13815248844526692155868802298129e-15,
            -.21916688282900363984955142264149e-16,
            .34904658524827565638313923706880e-17 };
    /**
     *  Evaluate a Chebyschev series
     */
    static double csevl(double x, double coef[]) {
        double b0, b1, b2, twox;
        int i;
        b1 = 0.0;
        b0 = 0.0;
        b2 = 0.0;
        twox = 2.0 * x;
        for (i = coef.length - 1; i >= 0; i--) {
            b2 = b1;
            b1 = b0;
            b0 = twox * b1 - b2 + coef[i];
        }
        return 0.5 * (b0 - b2);
    }
    /**
     *  Returns the inverse (arc) hyperbolic sine of a double.
     *  @param  x   A double value.
     *  @return  The arc hyperbolic sine of x.
     *  If x is NaN, the result is NaN.
     */
    public static double asinh(double x) {
        double ans;
        double y = Math.abs(x);

        if (Double.isNaN(x)) {
            ans = Double.NaN;
        } else if (y <= 1.05367e-08) {
            // 1.05367e-08 = Math.sqrt(EPSILON_SMALL)
            ans = x;
        } else if (y <= 1.0) {
            ans = x * (1.0 + csevl(2.0 * x * x - 1.0, ASINH_COEF));
        } else if (y < 94906265.62) {
            // 94906265.62 = 1/Math.sqrt(EPSILON_SMALL)
            ans = Math.log(y + Math.sqrt(y * y + 1.0));
        } else {
            ans = 0.69314718055994530941723212145818 + Math.log(y);
        }
        if (x < 0.0)
            ans = -ans;
        return ans;
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

    public static int min(int[] values) {
        int m = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m > values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static long min(long[] values) {
        long m = Long.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m > values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static float min(float[] values) {
        float m = Float.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m > values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static double min(double[] values) {
        double m = Double.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m > values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static int max(int[] values) {
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m < values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static long max(long[] values) {
        long m = Long.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m < values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static float max(float[] values) {
        float m = -Float.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m < values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    public static double max(double[] values) {
        double m = -Double.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (m < values[i]) {
                m = values[i];
            }
        }

        return m;
    }

    /*
     * Statische Methoden f�r den Umgang mit Komplexen-Zahlen.
     * Originalcode siehe nachstehenden Kommentar.
     */

    /*
     * -------------------------------------------------------------------------
     *  $Id$
     * -------------------------------------------------------------------------
     * Copyright (c) 1997 - 1998 by Visual Numerics, Inc. All rights reserved.
     *
     * Permission to use, copy, modify, and distribute this software is freely
     * granted by Visual Numerics, Inc., provided that the copyright notice
     * above and the following warranty disclaimer are preserved in human
     * readable form.
     *
     * Because this software is licenses free of charge, it is provided
     * "AS IS", with NO WARRANTY.  TO THE EXTENT PERMITTED BY LAW, VNI
     * DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
     * TO ITS PERFORMANCE, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
     * VNI WILL NOT BE LIABLE FOR ANY DAMAGES WHATSOEVER ARISING OUT OF THE USE
     * OF OR INABILITY TO USE THIS SOFTWARE, INCLUDING BUT NOT LIMITED TO DIRECT,
     * INDIRECT, SPECIAL, CONSEQUENTIAL, PUNITIVE, AND EXEMPLARY DAMAGES, EVEN
     * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. 
     *
     * -------------------------------------------------------------------------
     */

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.re + b.re, a.im + b.im);
    }

    public static Complex sub(Complex a, Complex b) {
        return new Complex(a.re - b.re, a.im - b.im);
    }

    public static Complex mult(Complex a, Complex b) {
        return new Complex(a.re * b.re - a.im * b.im,
                            a.re * b.im + a.im * b.re);
    }

    public static Complex mult(double a, Complex b) {
        return new Complex(a * b.re, a * b.im);
    }

    public static Complex div(Complex a, Complex b) {
        double den = b.re * b.re + b.im * b.im;
        return new Complex(
            (a.re * b.re + a.im * b.im) / den,
            (a.im * b.re - a.re * b.im) / den);
    }

    /** 
     *  Returns the absolute value (modulus) of a Complex, |z|. 
     *  @param  z   A Complex object.
     *  @return A double value equal to the absolute value of the argument.
     */
    public static double abs(Complex z) {
        double x = Math.abs(z.re);
        double y = Math.abs(z.im);

        if (Double.isInfinite(x) || Double.isInfinite(y))
            return Double.POSITIVE_INFINITY;

        if (x + y == 0.0) {
            return 0.0;
        } else if (x > y) {
            y /= x;
            return x * Math.sqrt(1.0 + y * y);
        } else {
            x /= y;
            return y * Math.sqrt(x * x + 1.0);
        }
    }

    /** 
     *  Returns the complex conjugate of a Complex object.
     *  @param  z   A Complex object.
     *  @return A newly constructed Complex initialized to complex conjugate of z.
     */
    public static Complex conjugate(Complex z) {
        return new Complex(z.re, -z.im);
    }

    /** 
     *  Returns the exponential of a Complex z, exp(z).
     *  @param  z   A Complex object.
     *  @return A newly constructed Complex initialized to exponential
     *          of the argument. 
     */
    public static Complex exp(Complex z) {
        Complex result = new Complex();

        double r = Math.exp(z.re);

        double cosa = Math.cos(z.im);
        double sina = Math.sin(z.im);
        if (Double.isInfinite(z.im)
            || Double.isNaN(z.im)
            || Math.abs(cosa) > 1) {
            cosa = sina = Double.NaN;

        }

        if (Double.isInfinite(z.re) || Double.isInfinite(r)) {
            if (z.re < 0) {
                r = 0;
                if (Double.isInfinite(z.im) || Double.isNaN(z.im)) {
                    cosa = sina = 0;
                } else {
                    cosa /= Double.POSITIVE_INFINITY;
                    sina /= Double.POSITIVE_INFINITY;
                }
            } else {
                r = z.re;
                if (Double.isNaN(z.im))
                    cosa = 1;
            }
        }

        if (z.im == 0.0) {
            result.re = r;
            result.im = z.im;
        } else {
            result.re = r * cosa;
            result.im = r * sina;
        }
        return result;
    }

    /** 
     *  Returns the argument (phase) of a Complex, in radians,
     *  with a branch cut along the negative real axis.
     *  @param  z   A Complex object.
     *  @return A double value equal to the argument (or phase) of a Complex.
     *          It is in the interval [-pi,pi].
     */
    public static double argument(Complex z) {
        return Math.atan2(z.im, z.re);
    }

    /** 
     *  Returns the logarithm of a Complex z,
     *  with a branch cut along the negative real axis.
     *  @param  z   A Complex object.
     *  @return  A newly constructed Complex initialized to logarithm
     *          of the argument. Its imaginary part is in the
     *          interval [-i*pi,i*pi].
     */
    public static Complex log(Complex z) {
        Complex result = new Complex();

        if (Double.isNaN(z.re)) {
            result.re = result.im = z.re;
            if (Double.isInfinite(z.im))
                result.re = Double.POSITIVE_INFINITY;
        } else if (Double.isNaN(z.im)) {
            result.re = result.im = z.im;
            if (Double.isInfinite(z.re))
                result.re = Double.POSITIVE_INFINITY;
        } else {
            result.re = Math.log(abs(z));
            result.im = argument(z);
        }
        return result;
    }

    /** 
     *  Returns the square root of a Complex,
     *  with a branch cut along the negative real axis.
     *  @param  z   A Complex object.
     *  @return A newly constructed Complex initialized
     *          to square root of z. Its real part is
     *          non-negative.
     */
    public static Complex sqrt(Complex z) {
        Complex result = new Complex();

        if (Double.isInfinite(z.im)) {
            result.re = Double.POSITIVE_INFINITY;
            result.im = z.im;
        } else if (Double.isNaN(z.re)) {
            result.re = result.im = Double.NaN;
        } else if (Double.isNaN(z.im)) {
            if (Double.isInfinite(z.re)) {
                if (z.re > 0) {
                    result.re = z.re;
                    result.im = z.im;
                } else {
                    result.re = z.im;
                    result.im = Double.POSITIVE_INFINITY;
                }
            } else {
                result.re = result.im = Double.NaN;
            }
        } else {
            // Numerically correct version of formula 3.7.27
            // in the NBS Hanbook, as suggested by Pete Stewart.
            double t = abs(z);

            if (Math.abs(z.re) <= Math.abs(z.im)) {
                // No cancellation in these formulas
                result.re = Math.sqrt(0.5 * (t + z.re));
                result.im = Math.sqrt(0.5 * (t - z.re));
            } else {
                // Stable computation of the above formulas
                if (z.re > 0) {
                    result.re = t + z.re;
                    result.im = Math.abs(z.im) * Math.sqrt(0.5 / result.re);
                    result.re = Math.sqrt(0.5 * result.re);
                } else {
                    result.im = t - z.re;
                    result.re = Math.abs(z.im) * Math.sqrt(0.5 / result.im);
                    result.im = Math.sqrt(0.5 * result.im);
                }
            }
            if (z.im < 0)
                result.im = -result.im;
        }
        return result;
    }

    public static Complex sqr(Complex x) {
        return x.mult(x);
    }

    /** 
     *  Returns the Complex z raised to the x power,
     *  with a branch cut for the first parameter (z) along the
     *  negative real axis.
     *  @param  z   A Complex object.
     *  @param  x   A double value.
     *  @return A newly constructed Complex initialized to z to the power x.
     */
    public static Complex pow(Complex z, double x) {
        double absz = abs(z);
        Complex result = new Complex();

        if (absz == 0.0) {
            result = z;
        } else {
            double a = argument(z);
            double e = Math.pow(absz, x);
            result.re = e * Math.cos(x * a);
            result.im = e * Math.sin(x * a);
        }
        return result;
    }

    /** 
     *  Returns the Complex x raised to the Complex y power. 
     *  @param  x   A Complex object.
     *  @param  y   A Complex object.
     *  @return A newly constructed Complex initialized
     *          to x<SUP><FONT SIZE="1">y</FONT></SUP><FONT SIZE="3">.
     */
    public static Complex pow(Complex x, Complex y) {
        return exp(times(y, log(x)));
    }

    /** 
     *  Returns the product of two Complex objects, x*y. 
     *  @param  x   A Complex object.
     *  @param  y   A Complex object.
     *  @return A newly constructed Complex initialized to x*y.
     */
    private static Complex times(Complex x, Complex y) {
        Complex t =
            new Complex(x.re * y.re - x.im * y.im, x.re * y.im + x.im * y.re);
        if (Double.isNaN(t.re) && Double.isNaN(t.im))
            timesNaN(x, y, t);
        return t;
    }

    /**
     *  Recovers infinities when computed x*y = NaN+i*NaN.
     *  This code is not part of times(), so that times
     *  could be inlined by an optimizing compiler.
     *  <p>
     *  This algorithm is adapted from the C9x Annex G:
     *  "IEC 559-compatible complex arithmetic."
     *  @param  x   First Complex operand.
     *  @param  y   Second Complex operand.
     *  @param  t   The product x*y, computed without regard to NaN.
     *              The real and/or the imaginary part of t is
     *              expected to be NaN.
     *  @return The corrected product of x*y.
     */
    private static void timesNaN(Complex x, Complex y, Complex t) {
        boolean recalc = false;
        double a = x.re;
        double b = x.im;
        double c = y.re;
        double d = y.im;

        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            // x is infinite
            a = copysign(Double.isInfinite(a) ? 1.0 : 0.0, a);
            b = copysign(Double.isInfinite(b) ? 1.0 : 0.0, b);
            if (Double.isNaN(c))
                c = copysign(0.0, c);
            if (Double.isNaN(d))
                d = copysign(0.0, d);
            recalc = true;
        }

        if (Double.isInfinite(c) || Double.isInfinite(d)) {
            // x is infinite
            a = copysign(Double.isInfinite(c) ? 1.0 : 0.0, c);
            b = copysign(Double.isInfinite(d) ? 1.0 : 0.0, d);
            if (Double.isNaN(a))
                a = copysign(0.0, a);
            if (Double.isNaN(b))
                b = copysign(0.0, b);
            recalc = true;
        }

        if (!recalc) {
            if (Double.isInfinite(a * c)
                || Double.isInfinite(b * d)
                || Double.isInfinite(a * d)
                || Double.isInfinite(b * c)) {
                // Change all NaNs to 0
                if (Double.isNaN(a))
                    a = copysign(0.0, a);
                if (Double.isNaN(b))
                    b = copysign(0.0, b);
                if (Double.isNaN(c))
                    c = copysign(0.0, c);
                if (Double.isNaN(d))
                    d = copysign(0.0, d);
                recalc = true;
            }
        }

        if (recalc) {
            t.re = Double.POSITIVE_INFINITY * (a * c - b * d);
            t.im = Double.POSITIVE_INFINITY * (a * d + b * c);
        }
    }

    private static double copysign(double a, double b) {
        double abs = Math.abs(a);
        return ((b < 0) ? -abs : abs);
    }

    /** 
     *  Returns a double dividied by this Complex object, x/y. 
     *  @param  x   The numerator, a double.
     *  @return A newly constructed Complex initialized to x/y.
     */
    public static Complex div(double x, Complex y) {
        double den, t;
        Complex z;
        if (Math.abs(y.re) > Math.abs(y.im)) {
            t = y.im / y.re;
            den = y.re + y.im * t;
            z = new Complex(x / den, -x * t / den);
        } else {
            t = y.re / y.im;
            den = y.im + y.re * t;
            z = new Complex(x * t / den, -x / den);
        }
        return z;
    }

    /*
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     */
     
     
     public static Complex min(Complex a, Complex b) {
        double re = Math.min(a.re, b.re);
        double im = Math.min(a.im, b.im);
     
        return new Complex(re, im);   
     }
     
     public static Complex max(Complex a, Complex b) {
        double re = Math.max(a.re, b.re);
        double im = Math.max(a.im, b.im);
     
        return new Complex(re, im);        
     }

}






