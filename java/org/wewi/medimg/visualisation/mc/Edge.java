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

/*
 * Edge.java
 *
 * Created on 10. Juni 2002, 16:57
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Edge {
    Vertex v1, v2;
    
    /** Creates a new instance of Edge */
    Edge() {
    }
    
    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public Vertex getV1() {
        return v1;
    }
    
    public Vertex getV2() {
        return v2;
    }
    
    public int hashCode() {
        int result = 37;
        result = 17*result + v1.hashCode();
        result = 17*result + v2.hashCode();
        return result;
    }
    
    public boolean equals(Object edge) {
        if (!(edge instanceof Edge)) {
            return false;
        }
        Edge e = (Edge)edge;
        return v1.equals(e.v1) && v2.equals(e.v2);
    }
    
}
