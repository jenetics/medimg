/*
 * Graph.java
 *
 * Created on 10. Juni 2002, 17:10
 */

package org.wewi.medimg.visualisation.mc;

import org.wewi.medimg.util.NullIterator;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
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
    
    public Iterator getVertices() {
        return vertices.keySet().iterator();
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
    
    /**
     * Liefert jene Dreiecke, zu denen dieser Punkt (v) gehört.
     */
    public Iterator getTriangles(Vertex v) {
        return ((VertexTriangles)vertices.get(v)).getTriangles();
    }
    
    /**
     * Liefert jenes Polygon, das bei einem Entfernen
     * des Knoten v entstehen würde.
     */
    public StarshapedPolygon getPolygon(Vertex v) {
        Triangle t;
        Vertex v1 = null, v2 = null;
        Vector e = new Vector();
        for (Iterator it = ((VertexTriangles)vertices.get(v)).getTriangles(); it.hasNext();) {
            t = (Triangle)it.next();
            if (v.equals(t.a)) {
                v1 = t.b;
                v2 = t.c;
            } else if (v.equals(t.b)) {
                v1 = t.a;
                v2 = t.c;
            } else if (v.equals(t.c)) {
                v1 = t.a;
                v2 = t.b;
            }
            e.add(new Edge(v1, v2));
        }        
        
        Edge[] edges = new Edge[e.size()];
        e.toArray(edges);
        
        return new StarshapedPolygon(edges);
    }
    
    public int getNoOfVertices() {
        return vertices.size();
    }
    
    public int getNoOfTriangles() {
        return triangles.size();
    }
}
