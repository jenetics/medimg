/*
 * Edge.java
 *
 * Created on 10. Juni 2002, 16:57
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
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
