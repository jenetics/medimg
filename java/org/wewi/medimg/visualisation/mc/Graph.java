/*
 * Graph.java
 *
 * Created on 10. Juni 2002, 17:10
 */

package org.wewi.medimg.visualisation.mc;

import org.wewi.medimg.util.NullIterator;

import java.util.HashSet;
import java.util.Hashtable;
//import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Graph {
    private class VertexTriangles {
        private HashSet triangles = new HashSet();
        
        public VertexTriangles() {
        }
        
        public void addTriangle(Triangle t) {
            if (triangles.contains(t)) {
                return;
            }
            triangles.add(t);
        }
        
        public void removeTriangle(Triangle t) {
            triangles.remove(t);
        }
        
        public boolean contains(Triangle t) {
            return triangles.contains(t);
        }
        
        public Iterator getTriangles() {
            return triangles.iterator();
        }
    }
    
    
    private Hashtable vertices;
    private HashSet triangles;
    
    /** Creates a new instance of Graph */
    public Graph() {
        vertices = new Hashtable(2000);
        triangles = new HashSet(1000);
    }
    
    public void addVertex(Vertex v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.put(v, new VertexTriangles());
    }
    
    public void removeVertex(Vertex v) {
        if (!vertices.contains(v)) {
            return;
        }
        //Wird ein Knoten entfernt, so müssen auch alle Dreiecke
        //entfernt werden, zu denen dieser Knoten gehört.
        Triangle t;
        for (Iterator it = ((VertexTriangles)vertices.get(v)).getTriangles(); it.hasNext();) {
            t = (Triangle)it.next();
            removeTriangle(t);
        }
        
        vertices.remove(v);
    }
    
    public boolean contains(Vertex v) {
        return vertices.contains(v);
    }
    
    public void addTriangle(Triangle t) {
        if (triangles.contains(t)) {
            return;
        }
        addVertex(t.a);
        addVertex(t.b);
        addVertex(t.c);
        
        triangles.add(t);
        
        ((VertexTriangles)vertices.get(t.a)).addTriangle(t);
        ((VertexTriangles)vertices.get(t.b)).addTriangle(t);
        ((VertexTriangles)vertices.get(t.c)).addTriangle(t);
    }
    
    public void removeTriangle(Triangle t) {
        if (!(triangles.contains(t))) {
            return;
        }
        
        triangles.remove(t);
        
        ((VertexTriangles)vertices.get(t.a)).removeTriangle(t);
        ((VertexTriangles)vertices.get(t.b)).removeTriangle(t);
        ((VertexTriangles)vertices.get(t.c)).removeTriangle(t);
    } 
    
    public boolean contains(Triangle t) {
        return triangles.contains(t);
    }
    
    public Iterator getTriangles(Vertex v) {
        return ((VertexTriangles)vertices.get(v)).getTriangles();
    }
}
