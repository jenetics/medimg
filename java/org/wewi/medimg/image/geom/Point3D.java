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
 * Point3D.java
 *
 * Created on 18. Februar 2002, 22:34
 */

package org.wewi.medimg.image.geom;

import org.wewi.medimg.util.Immutable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Point3D implements Point, Immutable {
    private final int[] point;

    public Point3D(int cx, int cy, int cz) {
        point = new int[3];
        point[0] = cx;
        point[1] = cy;
        point[2] = cz;
    }
    
    public Point3D(Point3D p) {
        this(p.point[0], p.point[1], p.point[2]);
    }
    
    public int getOrdinate(int i) {
        return point[i];
    }
    
    public int getDimensions() {
        return 3;
    }  
    
    public int getX() {
        return point[0];
    }
    
    public int getY() {
        return point[1];
    }
    
    public int getZ() {
        return point[2];
    }
    
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Point3D)) {
            return false;
        }
        Point3D p = (Point3D)o;
        return (p.point[0] == point[0] && 
                p.point[1] == point[1] && 
                p.point[2] == point[2]);
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + point[0];
        result = 37*result + point[1];
        result = 37*result + point[2];
        return result;
    }
    
    public Object clone() {
        return new Point3D(this);
    }  
    
    public String toString() {
        return "x: " + point[0] + " y: " + point[1] + " z: " + point[2];    
    }
    
    
    
    
    
      
}
