/*
 * Point2DIterator.java
 *
 * Created on 18. Februar 2002, 20:45
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class PointIterator implements Iterator {
    private Point[] neighbors;
    private int ncount;
    private int itpointer;

    public PointIterator(int size) {
        neighbors = new Point[size];
        ncount = 0;
        itpointer = 0;
    }
    
    public void addPoint(Point p) {
        //Null Werte werden nicht eingefügt!!!
        if (p == null) {
            return;
        }
        neighbors[ncount] = p;
        ++ncount;
    }

    public boolean hasNext() {
        return itpointer < ncount;
    }
    
    public Object next() {
        Object o =  neighbors[itpointer];
        ++itpointer;
        return o;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
}
