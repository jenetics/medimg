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
        if (!normal.equals(Point.NULL_POINT)) {
            return normal;
        }
        
        float abx = b.x - a.x;
        float aby = b.y - a.y;
        float abz = b.z - a.z;
        float acx = c.x - a.x;
        float acy = c.y - a.y;
        float acz = c.z - a.z;
        //System.out.println(" triangle abx " + abx + " acx " + acx + " abz " + abz + " acz " + acz);           
        normal.x = -abz * acy + aby * acz;
        normal.y = abz * acx - abx * acz;
        normal.z = abx * acy - aby * acx;
        //System.out.println(" triangle normal.x " + normal);              
        float norm = (float)Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);
        normal.x = normal.x / norm;
        normal.y = normal.y / norm;
        normal.z = normal.z / norm;

        return normal;
    }
    
    public boolean contains(Point p) {
        return true;
    }
    
    public String toString() {
        return a.toString() + " " + b.toString() + " " + c.toString() + " " + normal.toString();
    }
    
}
