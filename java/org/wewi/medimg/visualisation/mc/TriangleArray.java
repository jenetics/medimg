/*
 * TriangleArray.java
 *
 * Created on March 18, 2002, 4:35 PM
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TriangleArray {
    private Vector triangles;
    
    /** Creates a new instance of TriangleArray */
    public TriangleArray() {
        triangles = new Vector(100);
    }
    
    
    public void add(Triangle t) {
        triangles.add(t);
    }
    
    public int size() {
        return triangles.size();
    }
    
    public Iterator iterator() {
        return triangles.iterator();
    }
}
