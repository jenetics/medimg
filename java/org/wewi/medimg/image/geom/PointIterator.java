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
