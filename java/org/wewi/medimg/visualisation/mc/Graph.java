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
 * Graph.java
 *
 * Created on 10. Juni 2002, 17:10
 */

package org.wewi.medimg.visualisation.mc;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/** 
 * Diese Klasse enthält die, beim Marchin Cube
 * Algorithmus erzeugten, Dreiecke ({@link Triangle }) und
 * Knoten ({@link Vertex }). </p>
 * Dabei können entweder Knoten oder Dreiecke ---
 * ein Dreieck wird durch seine drei Eckpunkte
 * definiert --- in den Graph eingefügt werden.
 * Ein Knoten wird nur dann eingefügt, wenn er
 * noch nicht enthalten ist. Das gleiche gilt beim
 * Einfügen eines Dreiecks. Wird ein Dreieck engefügt,
 * werden zuerst die drei Knoten, aus denen das Dreieck
 * besteht, eingefügt. </p>
 * Wird ein Dreieck aus dem Graph entfernt, so werden auch
 * gleichzeitig die dazugehörigen Knoten entfernt. Ebenso wird
 * beim Entfernen eines Punktes alle jene Dreiecke entfernt,
 * zu denene der Knoten insident ist.
 *
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Graph {
    /**
     * Hilfsdatenstruktur, die zu einem Knoten
     * die Dreiecke speichert, zu denen der 
     * Knoten gehört.
     */
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
        
        public int size() {
            return triangles.size();
        }
    }
    
    /**
     * Container, der die Knoten aufnimmt
     */
    private Hashtable vertices;
    /**
     * Container, der die Dreieck aufnimmt
     */
    private HashSet triangles;
    
    /**
     * Ein neuer Graph wird erzeugt.
     */
    public Graph() {
        this(1000, 1000);
    }
    
    /**
     * Erzeugen eines neuen Graphen.
     * @param vsize die initiale Größe des Containers, der die
     *              Knoten aufnimmt.
     * @param tsize die initiale Größe des Containders, der die
     *              Dreiecke aufnimmt.
     */
    public Graph(int vsize, int tsize) {
        vertices = new Hashtable(vsize);
        triangles = new HashSet(tsize);
    }
    
    /**
     * Einfügen eines Knotens in den Graphen. Es werden
     * nur Knoten eingefügt, die noch nicht im Graphen
     * enthalten sind, d.h. bereits enthaltene Knoten werden
     * nicht übberschrieben.
     *
     * @param v Knoten der Eingefügt wird
     * @return true, wenn der Knoten eingefügt wurde,
     *               d.h. wenn der Knoten noch nicht im
     *               Graph enthalten war.
     */
    public boolean addVertex(Vertex v) {
        //Ein Knoten wird nur dann eingefügt, wenn er noch nicht 
        //im Hashtable vorhanden ist.
        if (vertices.containsKey(v)) {
            return false;
        }
        vertices.put(v, new VertexTriangles());
        return true;
    }
    
    public boolean removeVertex(Vertex v) {
        //Es ist sinnvoll, nur Knoten zu entfernen, 
        //welche auch vorhanden sind.
        if (!vertices.containsKey(v)) {
            return false;
        }

        Triangle t;
        //Wird ein Knoten entfernt, so müssen auch alle Dreiecke
        //entfernt werden, zu denen dieser Knoten gehört.        
        for (Iterator it = ((VertexTriangles)vertices.get(v)).getTriangles(); it.hasNext();) {
            t = (Triangle)it.next();
            //Zuerst aus dem Iterator entfernen, sonst wird er Ungültig.
            it.remove();
            removeTriangle(t);
        }

        //Ganz am Schluß kann der Knoten entfernt werden.
        return (vertices.remove(v) != null);
    }
    
    public boolean contains(Vertex v) {
        return vertices.contains(v);
    }
    
    public Iterator getVertices() {
        return vertices.keySet().iterator();
    }
    
    public boolean addTriangle(Triangle t) {
        //Ein Dreieck wird nur eingefügt, wenn es noch nicht 
        //vorhanden ist.
        if (triangles.contains(t)) {
            return false;
        }
        addVertex(t.a);
        addVertex(t.b);
        addVertex(t.c);
        
        triangles.add(t);
        
        ((VertexTriangles)vertices.get(t.a)).addTriangle(t);
        ((VertexTriangles)vertices.get(t.b)).addTriangle(t);
        ((VertexTriangles)vertices.get(t.c)).addTriangle(t);
        
        return true;
    }
    
    public boolean removeTriangle(Triangle t) {
        if (!(triangles.contains(t))) {
            return false;
        }
        
        triangles.remove(t);
        
        ((VertexTriangles)vertices.get(t.a)).removeTriangle(t);
        ((VertexTriangles)vertices.get(t.b)).removeTriangle(t);
        ((VertexTriangles)vertices.get(t.c)).removeTriangle(t);
        
        return true;
    } 
    
    public boolean contains(Triangle t) {
        return triangles.contains(t);
    }

    /**
     * Liefert die Anzahl der Dreiecke, zu denen dieser Punkt (v) gehört.
     */
    public int getNoOfIncidentTriangles(Vertex v) {
        if (!vertices.containsKey(v)) {
            return 0;
        }
        return ((VertexTriangles)vertices.get(v)).size();
    }
        
    /**
     * Liefert jene Dreiecke, zu denen dieser Punkt (v) gehört.
     */
    public Iterator getIncidentTriangles(Vertex v) {
        return ((VertexTriangles)vertices.get(v)).getTriangles();
    }
    
    public Iterator getTriangles() {
        return triangles.iterator();
    }
    
    /**
     * Liefert jenes Polygon, das bei einem Entfernen
     * des Knoten v entstehen würde.
     */
    StarshapedPolygon getPolygon(Vertex v) {
        if (!vertices.containsKey(v)) {
            return null;
        }        
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
        
        return new StarshapedPolygon(edges, v);
    }
    
    public int getNoOfVertices() {
        return vertices.size();
    }
    
    public int getNoOfTriangles() {
        return triangles.size();
    }
}
