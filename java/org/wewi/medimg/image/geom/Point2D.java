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
 * Point2D.java
 *
 * Created on 18. Februar 2002, 20:20
 */

package org.wewi.medimg.image.geom;

import org.wewi.medimg.util.Immutable;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public final class Point2D implements Point, Immutable {
    private final int[] point;
    
    public Point2D(int cx, int cy) {
        point = new int[2];
        point[0] = cx;
        point[1] = cy;
    }
    
    public Point2D(Point2D p) {
        this(p.point[0], p.point[1]);
    }
    
    public int getOrdinate(int i) { 
        return point[i];
    }   
    
    public int getDimensions() {
        return 2;
    }    

    public int getX() {
        return point[0];
    }
    
    public int getY() {
        return point[1];
    }
       
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Point2D)) {
            return false;
        }
        Point2D p = (Point2D)o;
        return (p.point[0] == point[0] && p.point[1] == point[1]);
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + point[0];
        result = 37*result + point[1];
        return result;
    }
    
    public Object clone() {
        return new Point2D(this);
    }
    
}
