/*
 * Triangle.java
 *
 * Created on 10. Juni 2002, 17:05
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Triangle {
    Vertex a, b, c;
    Point normal;
    
    Triangle() {
    }
    
    public Triangle(Vertex a, Vertex b, Vertex c, Point normal) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal = normal;
    }
    
    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
        normal = new Point();
    }
    
    public Vertex getA() {
        return a;
    }
    
    public Vertex getB() {
        return b;
    }
    
    public Vertex getC() {
        return c;
    }
    
    public Point getNormal() {
        return normal;
    }
    
    public String toString() {
        return a.toString() + " " + b.toString() + " " + c.toString() + " " + normal.toString();
    }
    
}
