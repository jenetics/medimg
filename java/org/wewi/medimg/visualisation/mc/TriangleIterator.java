/*
 * TriangleIterator.java
 *
 * Created on 20. März 2002, 21:08
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class TriangleIterator implements Iterator {
    private Triangle[] triangles;
    private int ncount;
    private int itpointer;

    public TriangleIterator(int size) {
        triangles = new Triangle[size];
        ncount = 0;
        itpointer = 0;
    }
    
    public void addTriangle(Triangle t) {
        //Null Werte werden nicht eingefügt!!!
        if (t == null) {
            return;
        }
        triangles[ncount] = t;
        ++ncount;
    }

    public boolean hasNext() {
        return itpointer < ncount;
    }
    
    public Object next() {
        Object o =  triangles[itpointer];
        ++itpointer;
        return o;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
}
