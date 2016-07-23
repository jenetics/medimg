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
 * Vertex.java
 *
 * Created on March 18, 2002, 4:42 PM
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Point {
    static final Point NULL_POINT = new Point(0, 0, 0);
    /**
     * Mit dieser Konstante wird die Genauigkeit auf
     * zwei Nachkommastellen beschränkt (RATIO = 100).
     */
    static final int RATIO = 100;
    float x = 0, y = 0, z = 0;
    
    Point() {
    }
    
    public Point(float xcoord, float ycoord, float zcoord) {
        //Die Genauigkeit der Koordinaten wird so auf eine gewisse
        //Anzahl von Nachkommastellen beschränkt. Dies ist notwendig,
        //wenn nahe beieinander liegende Punkte, mit equals, auf
        //Gleichheit geprüft werden.
        x = (float)((int)(xcoord*RATIO))/(float)RATIO;
        y = (float)((int)(ycoord*RATIO))/(float)RATIO;
        z = (float)((int)(zcoord*RATIO))/(float)RATIO;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public boolean equals(Object point) {
        if (!(point instanceof Point)) {
            return false;
        }
        Point p = (Point)point;
        return Float.floatToIntBits(p.x) == Float.floatToIntBits(x) &&
               Float.floatToIntBits(p.y) == Float.floatToIntBits(y) &&
               Float.floatToIntBits(p.z) == Float.floatToIntBits(z);
    }
    
    public int hashCode() {
        int result = 37;
        result = 17*result + Float.floatToIntBits(x);
        result = 17*result + Float.floatToIntBits(y);
        result = 17*result + Float.floatToIntBits(z);
        return result;
    }
    
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
