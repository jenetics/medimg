/**
 * Polygon.java
 *
 * Created on 25. Februar 2002, 17:09
 */

package org.wewi.medimg.math;

//import java.util.Arrays;
//import java.util.Random;

//import org.wewi.medimg.util.Timer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Polynomial implements Function {
    protected double[] an;
    
    protected Polynomial() {
    }
    
    public Polynomial(double[] an) {
        this.an = new double[an.length];
        System.arraycopy(an, 0, this.an, 0, an.length);
    }

    public double[] eval(double[] arg) {
        double[] val = new double[1];
        val[0] = eval(arg[0]);
        return val;
    }
    
    public double eval(double x) {
        //Horner-Schema
        double result = an[an.length-1];
        for (int i = an.length-2; i >= 0; i--) {
            result = result*x + an[i];
        }
        return result;
    }
  
    
    
    /*
    public double evalPrimitive(double x) {
        double result = 0;
        for (int i = 0; i < an.length; i++) {
            result += Math.pow(x, i)*an[i];
        }
        return result;
    }
    
    
    
    public static void main(String[] args) {
        double[] an = new double[10];
        Arrays.fill(an, 2);
        int IT = 10;
        
        
        Timer timer = new Timer("Polynom");
        timer.start();
        Polynom poly = new Polynom(an);
        double erg;
        double arg;
        double[] ea = new double[1];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < IT; i++) {
            //ea[0] = rand.nextDouble();
            //ea = poly.eval(ea);
            arg = rand.nextDouble();
            erg = poly.eval(arg);
            System.out.print(erg);
            System.out.print(":");
            System.out.println(poly.evalPrimitive(arg));
            if (erg != poly.evalPrimitive(arg)) {
                System.out.println("asdfasd");
            }
        }
        timer.stop();
        timer.print();
        
    }
     */
}
