/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Neighborhood2D8.java
 *
 * Created on 18. Februar 2002, 22:04
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

import org.wewi.medimg.image.Dimension;

/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
public final class Neighborhood2D8 implements Neighborhood {
    private int minX, minY;
    private int maxX, maxY;
    
    public Neighborhood2D8(Dimension dim) {
        this(dim.getMinX(), dim.getMinY(), dim.getMaxX(), dim.getMaxY());   
    }
    
    public Neighborhood2D8(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Iterator getNeighbors(Point p) {
        PointIterator iterator = new PointIterator(8);
        
        int x = p.getOrdinate(0);
        int y = p.getOrdinate(1);

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
