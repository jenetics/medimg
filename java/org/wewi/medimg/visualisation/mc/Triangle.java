/*
 * Triangle.java
 *
 * Created on March 18, 2002, 4:40 PM
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class Triangle {
    private final Point A;
    private final Point B;
    private final Point C;
    private final Point n;
    
    public Triangle(Point A, Point B, Point C, Point n) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.n = n;
    }
    
    public Triangle(Point A, Point B, Point C) {
        this(A, B, C, A);
    }
    
    public Point getA() {
        return A;
    }
    
    public Point getB() {
        return B;
    }
    
    public Point getC() {
        return C;
    }
    
    public Point getNormal() {
        return n;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(A).append(",").append(B).append(",").append(C);
        return buffer.toString();
    }
}
