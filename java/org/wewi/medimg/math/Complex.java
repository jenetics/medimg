/**
 * Complex.java
 * 
 * Created on 12.12.2002, 00:26:59
 *
 */
package org.wewi.medimg.math;

import java.text.NumberFormat;

import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Complex implements Immutable, Cloneable {
    public static final Complex NULL = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);
    
    double re;
    double im;
    
    public Complex() {
        re = 0;
        im = 0;    
    }
    
    public Complex(double re) {
        this.re = re;
        im = 0;    
    }
    
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;    
    }

    /**
     * Constructor for Complex.
     */
    public Complex(Complex number) {
        this.re = number.re;
        this.im = number.im;
    }


    public Complex add(Complex n) {
        return new Complex(re + n.re, im + n.im);
    }


    public Complex sub(Complex n) {
        return new Complex(re - n.re, im - n.im);
    }


    public Complex mult(Complex n) {
        return new Complex(re*n.re - im*n.im, re*n.im + im*n.re);
    }


    public Complex div(Complex n) {
        double den = n.re*n.re + n.im*n.im;
        return new Complex((re*n.re + im*n.im)/den, (im*n.re - re*n.im)/den);
    }
    
    /**
     * Returns the im.
     * @return double
     */
    public double getIm() {
        return im;
    }

    /**
     * Returns the re.
     * @return double
     */
    public double getRe() {
        return re;
    }
    
    public int hashCode() {
        long reBits = Double.doubleToLongBits(re);
        long imBits = Double.doubleToLongBits(im);
        return (int)((reBits^imBits)^((reBits^imBits) >> 32));
    }
    
    public boolean equals(Complex z) {
        return (re == z.re  &&  im == z.im);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;    
        }
        
        if (obj instanceof Complex) {
            return equals((Complex)obj);
        } else {
            return false;
        }
    }        
    
    public String toString() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(5);
        format.setMinimumFractionDigits(5);
        
        return "(" + format.format(re) + "; " + format.format(im) + ")";    
    }

    public Object clone() {
        return new Complex(this);    
    }

}





