/*
 * StarshapedPolygon.java
 *
 * Created on 11. Juni 2002, 19:41
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;
import java.util.Vector;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Arrays;

import org.wewi.medimg.util.NullIterator;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import cern.colt.matrix.linalg.LUDecomposition;


/**
 *
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * @version 0.1
 */
class StarshapedPolygon {
    
    private Vertex[] vertices;
    private Edge[] edges;
    private boolean closed = false;
    private Vertex nucleus;
    private Vector triangles;
    private static final float EPSILON = 0.01f;
    /** Creates a new instance of StarshapedPolygon */
    public StarshapedPolygon(Edge[] edges, Vertex nucleus) {
        this.edges = edges;
        this.nucleus = nucleus;
        closed = close();
    }
    
    public void triangulate() {
        if (!isClosed()) {
            return;
        }
        
        triangles = new Vector();
        if (vertices.length == 3) {
            triangles.add(new Triangle(vertices[0], vertices[1], vertices[2]));
            return;
        }
        int point1 = 0, point2 = 1, point3 = 2;
        Triangle t;
        Vector vertexVector = new Vector(Arrays.asList(vertices));
        
        while (true) {
            t = new Triangle((Vertex)vertexVector.elementAt(point1), 
                             (Vertex)vertexVector.elementAt(point2), 
                             (Vertex)vertexVector.elementAt(point3));
            if (isValidTriangle(t, nucleus)) {
                triangles.add(t);
                vertexVector.remove(point2);

                //point2 = point3;
                point3 = (point3) % vertexVector.size();
                point1 = (point3 + vertexVector.size() - 2) % vertexVector.size();
                point2 = (point3 + vertexVector.size() - 1) % vertexVector.size();
                System.out.println("hhh " + vertexVector.size() + " point1 " + point1 + 
                                   " point2 " + point2 + " point3 " + point3 );
                System.out.println(" t " + t);
            } else {
                point1 = point2;
                point2 = point3;
                point3 = (point3 + 1) % vertexVector.size();
                System.out.println("hhh im else " + vertexVector.size() + " point1 " + point1 + 
                                   " point2 " + point2 + " point3 " + point3 );                
            }
            if (vertexVector.size() == 3) {
                triangles.add(new Triangle((Vertex)vertexVector.elementAt(0), 
                                           (Vertex)vertexVector.elementAt(1), 
                                           (Vertex)vertexVector.elementAt(2)));
                return;            
            }
        }
        
    }
    
    private boolean isValidTriangle(Triangle t, Vertex nucleus) {
        Vertex p = nucleus;
        Point n = t.getNormal();
        System.out.println(" normal " + n);        
        // Projektion des Nukleus auf die Ebene des Dreiecks
        float d = t.a.x * n.x + t.a.y * n.y + t.a.z * n.z;
        System.out.println(" d " + d);
        float l = (d - p.x * n.x - p.y * n.y - p.z * n.z) /
                  (n.x * n.x + n.y * n.y + n.z * n.z);
        System.out.println(" l " + l);
        float qx = p.x + l * n.x;
        float qy = p.y + l * n.y;
        float qz = p.z + l * n.z;
        Vertex q = new Vertex(qx, qy, qz);
        System.out.println(" q " + q);
        // reduzieren die Koordinate, bei der der Normalvektor die größte Abmessung hat
        float p1x, p1y;
        float p2x, p2y;
        float p3x, p3y;
        float p4x, p4y;
        if (Math.abs(n.x) >= Math.abs(n.y) && Math.abs(n.x) >= Math.abs(n.z)) {
            p1x = t.a.y; p1y = t.a.z;
            p2x = t.b.y; p2y = t.b.z;
            p3x = t.c.y; p3y = t.c.z;
            p4x = qy;    p4y = qz;
        } else if (Math.abs(n.y) >= Math.abs(n.x) && Math.abs(n.y) >= Math.abs(n.z)) {
            p1x = t.a.x; p1y = t.a.z;
            p2x = t.b.x; p2y = t.b.z;
            p3x = t.c.x; p3y = t.c.z;
            p4x = qx;    p4y = qz;            
        } else {
            p1x = t.a.x; p1y = t.a.y;
            p2x = t.b.x; p2y = t.b.y;
            p3x = t.c.x; p3y = t.c.y;
            p4x = qx;    p4y = qy;            
        }
               
        
        //Berechnen, ob die Ecke konvex
        float det1 = -p1y * p2x + p1x * p2y + p1y * p3x - p2y * p3x - p1x * p3y + p2x * p3y;
        float det2 = -p1y * p2x + p1x * p2y + p1y * p4x - p2y * p4x - p1x * p4y + p2x * p4y;
        System.out.println(" isValidTriangle: det1= " + det1 + " det2= " + det2);
        if (signum(det1) != signum(det2) ) {
            return false;
        }        
        // test ob Nucleus im Dreieck 
        float det3 = -p2y * p3x + p2x * p3y + p2y * p4x - p3y * p4x - p2x * p4y + p3x * p4y;
        float det4 = -p3y * p1x + p3x * p1y + p3y * p4x - p1y * p4x - p3x * p4y + p1x * p4y;
        System.out.println(" isValidTriangle: det3= " + det3 + " det4= " + det4); 
        if (Math.abs(det2) <= EPSILON) {
            det2 = -det3;
        }
        if (Math.abs(det3) <= EPSILON) {
            det3 = -det4;
        }
        if (Math.abs(det4) <= EPSILON) {
            det4 = -det2;
        }        
        System.out.println(" isValidTriangle: det3= " + det3 + " det4= " + det4);         
        if (signum(det2) != signum(det3) || signum(det2) != signum(det4)) {
            return true;
        } 
        return false;
    }
    
    private int signum(float a) {
        if (a >= 0) {
            return 1;
        }
        return -1;
    }
    
    public Iterator getTriangles() {
        if (triangles == null) {
            return new NullIterator();
        }
        return triangles.iterator();
    }
    
    public boolean isClosed() {
        return closed;
    }
    
    public void getEigenValues(double[] eigenValues) {
        DoubleFactory2D factory2D;
        factory2D = DoubleFactory2D.dense;
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;         
        DoubleMatrix2D data1 = factory2D.make(vertices.length, 3);  
        for (int i = 0; i < vertices.length; i++) {
            data1.setQuick(i, 0, vertices[i].x);
            data1.setQuick(i, 1, vertices[i].y);
            data1.setQuick(i, 2, vertices[i].z);
        }        
        int p = data1.columns(); //Dimension
        int n = data1.rows(); //Anzahl der Punkte
        DoubleMatrix1D cog = centreOfGravity(data1);
        //Berechnen der Kovarianzmatrix
        for (int i = 0; i < n; i++) {
            data1.viewRow(i).assign(cog, cern.jet.math.Functions.minus);
        }
        DoubleMatrix2D data2;
        DoubleMatrix2D covarianceMatrix = factory2D.make(p, p);
        data1.zMult(data1, covarianceMatrix, 1.0, 0.0, false, true);
        EigenvalueDecomposition eigen = new EigenvalueDecomposition(covarianceMatrix);
        DoubleMatrix1D eigenValues1D = factory1D.make(p);
        eigenValues1D = eigen.getRealEigenvalues();
        for (int i = 0; i < p; i++) {
            eigenValues[i] = eigenValues1D.getQuick(i);
        }     
        Arrays.sort(eigenValues);
    }
    
    /**
     * Berechnen des Schwerpunktes
     * @param data Daten, aus denen ein Schwerpunkt berechnet werden soll.
     * Die einzelnen Datenpunkte sind reihenweise angeordnet; in einer n * 3 Matrix
     * @return Schwerpunkt der Datenpunkte
     */
    private DoubleMatrix1D centreOfGravity(DoubleMatrix2D data) {
        int cols = data.columns();
        int rows = data.rows();
         Algebra alg = new Algebra();
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;
        DoubleMatrix1D cog = factory1D.make(cols);
        cog.assign(0.0);
        //DoubleMatrix1D row = factory1D.make(cols);        
        int i;
        for (i = 0; i < rows; i++) {
            DoubleMatrix1D row = data.viewRow(i);
            cog.assign(row, cern.jet.math.Functions.plus);
        }
        //System.out.println("cog" + cog);
        cog.assign(cern.jet.math.Functions.mult(1.0 / rows));
        return cog;
    }    
    
    private boolean close() {
        Vector polygonVertices = new Vector();
        HashSet verticesUsed = new HashSet();
        Hashtable h1 = new Hashtable();
        Hashtable h2 = new Hashtable();
        Vertex vertex;
        Vertex key;
        Vertex value;
        for (int i = 0; i < edges.length; i++) {
            if (!h1.containsKey(edges[i].v1)) {
                h1.put(edges[i].v1, edges[i].v2);  
            } else {
                h2.put(edges[i].v1, edges[i].v2);
            }
            if (!h1.containsKey(edges[i].v2)) {
                h1.put(edges[i].v2, edges[i].v1);  
            } else {
                h2.put(edges[i].v2, edges[i].v1);
            }            
           
        }

        key = edges[0].v1;
        polygonVertices.add(key);
        verticesUsed.add(key);
        //System.out.println(" Hallo ht1" + h1);
        //System.out.println(" Hallo ht2" + h2);
        boolean closed = false;
        do {
            value = (Vertex)h1.get(key);
            //System.out.println(" Hallo 1" + value);
            if (verticesUsed.contains(value)) {
                value = (Vertex)h2.get(key);
            } 
            if (value == null) {
                return false;
            }            
            if (verticesUsed.contains(value)) {
                //Wenn die Anzahl der Kanten mit der Anzahl
                //der Knoten übereinstimmt sind wir fertig.
                //Im adneren Fall ist das Polygon nicht geschlossen.
                if (verticesUsed.size() == edges.length) {
                    vertices = new Vertex[polygonVertices.size()];
                    polygonVertices.toArray(vertices);
                    return true;
                } else {
                    return false;
                }
            }
            key = value;
            polygonVertices.add(key);
            verticesUsed.add(key);
            
        } while (true);   
            
    }
    
    public static void main (String args[]) {
        Edge[] edge = new Edge[9];
        edge[1] = new Edge(new Vertex(0,0,0), new Vertex(20,0,0));
        edge[4] = new Edge(new Vertex(20,0,0), new Vertex(30,-20,0));
        edge[2] = new Edge(new Vertex(30,-20,0), new Vertex(40,0,0));
        edge[6] = new Edge(new Vertex(40,0,0), new Vertex(50,10,0)); 
        edge[0] = new Edge(new Vertex(50,10,0), new Vertex(70,10,0)); 
        edge[5] = new Edge(new Vertex(70,10,0), new Vertex(50,20,0)); 
        edge[8] = new Edge(new Vertex(50,20,0), new Vertex(35,55,0)); 
        edge[7] = new Edge(new Vertex(35,55,0), new Vertex(20,20,0)); 
        edge[3] = new Edge(new Vertex(20,20,0), new Vertex(0,0,0)); 
        /*edge[0] = new Edge(new Vertex(0,0,0), new Vertex(10,0,0));
        edge[1] = new Edge(new Vertex(10,0,0), new Vertex(10,10,0)); 
        edge[2] = new Edge(new Vertex(10,10,0), new Vertex(0,10,0)); 
        edge[3] = new Edge(new Vertex(0,10,0), new Vertex(0,0,0));  */       
        Vertex nuc = new Vertex(35, 15, 0);
        StarshapedPolygon stp = new StarshapedPolygon(edge, nuc);
        stp.triangulate();
        System.out.println(" Diese Polygon ist geschlossen: " + stp.isClosed()); 
        for ( int i = 0; i < stp.vertices.length; i++) {
            System.out.println(" Punkte " + stp.vertices[i]);
        }
        for ( Iterator it = stp.getTriangles(); it.hasNext(); ) {
            
            System.out.println(" Dreieck " + it.next());
        }
        
    }
    
}
