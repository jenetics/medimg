/*
 * Neighborhood2D8.java
 *
 * Created on 18. Februar 2002, 22:04
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
public final class Neighborhood2D8 implements Neighborhood {
    private int minX, minY;
    private int maxX, maxY;
    
    public Neighborhood2D8(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Iterator getNeighbors(Point p) {
        Point2D p2d = (Point2D)p;
        PointIterator iterator = new PointIterator(8);
        
        int x = p2d.getX();
        int y = p2d.getY();

        int xm1, ym1, xp1, yp1;
        xm1 = x-1;
        ym1 = y-1;
        xp1 = x+1;
        yp1 = y+1;
        
        if (!(xm1 < minX)) {
            if (!(ym1 < minY)) {
                iterator.addPoint(new Point2D(xm1, ym1));
            }
            iterator.addPoint(new Point2D(xm1, y));
            if (!(yp1 > maxY)) {
                iterator.addPoint(new Point2D(xm1, yp1));
            }
        }
        if (!(xp1 > maxX)) {
            if (!(ym1 < minY)) {
                iterator.addPoint(new Point2D(xp1, ym1));
            }
            iterator.addPoint(new Point2D(xp1, y));
            if (!(yp1 > maxY)) {
                iterator.addPoint(new Point2D(xp1, yp1));
            }
        }
        if (!(yp1 > maxY)) {
            iterator.addPoint(new Point2D(x, yp1));
        }
        if (!(ym1 < minY)) {
            iterator.addPoint(new Point2D(x, ym1));
        }
        
        return iterator;
    }
    
}
