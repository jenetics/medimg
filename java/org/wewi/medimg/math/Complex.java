/**
 * Complex.java
 * 
 * Created on 12.12.2002, 00:26:59
 *
 */
package org.wewi.medimg.math;

import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Complex implements Immutable {
    double re;
    double im;
    
    Complex() {
        re = 0;
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
        return new Complex((re*n.re+im*n.im)/den, (im*n.re-re*n.im)/den);
    }
    
    public double real() {
        return re;    
    }
    
    public double imag() {
        return im;    
    }
    
    public int hashCode() {
        long re_bits = Double.doubleToLongBits(re);
        long im_bits = Double.doubleToLongBits(im);
        return (int)((re_bits^im_bits)^((re_bits^im_bits)>>32));
    }    
    
    public String toString() {
        return "(" + re + "," + im + ")";    
    }


    
}
