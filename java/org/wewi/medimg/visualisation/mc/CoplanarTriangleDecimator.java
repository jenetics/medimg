/**
 * CoplanarTriangleDecimator.java
 *
 * Created on 12. Juni 2002, 15:43
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class CoplanarTriangleDecimator extends TriangleDecimator {
    
    private static final double EPSILON = 0.01;
    /** Creates new CoplanarTriangleDecimator */
    public CoplanarTriangleDecimator() {
    }
    
    public CoplanarTriangleDecimator(TriangleDecimator component) {
        super(component);
    }

    public void decimate(Graph graph) {
        super.decimate(graph);
   
        Vertex v;
        Triangle t;
        StarshapedPolygon p;
        double similarity = 0.0;
        double[] eigenValues = new double[3];
        int one = 0;
        int two = 0;
        int three = 0;
        Vector verticesToRemove = new Vector();
        Vector trianglesToAdd = new Vector(); 
        
        Vertex[] vertices = new Vertex[graph.getNoOfVertices()];
        int count = 0;
        for (Iterator it = graph.getVertices(); it.hasNext();) {
            vertices[count++] = (Vertex)it.next();
        }
        
        for (int i = 0; i < vertices.length; i++) {
            v = vertices[i];
            // Ein Punkt mit 2 Dreiecksnachbarn ist jedenfalls ein Punkt der Außenkontur
            // und darf daher nicht entfernt werden
            if (graph.getNoOfIncidentTriangles(v) <= 2) {
                one++;
                continue;
            }
            p = graph.getPolygon(v);
            if (p == null) {
                continue;
            }
            if (!p.isClosed()) {
                two++;
                continue;
            }
            // Eigenwerte nach Größe geordnet
            p.getEigenValues(eigenValues);
            similarity = ((eigenValues[0] * eigenValues[0]) /
                          (eigenValues[2] * eigenValues[2] + eigenValues[1] * eigenValues[1]));
            //System.out.println("similarity " + similarity );            
            if (similarity >= EPSILON) {
                three++;
                continue;
            }
                        //System.out.println("vor tri: " + graph.getNoOfVertices());
            p.triangulate();
                        //System.out.println("nach tri: " + graph.getNoOfVertices());
            graph.removeVertex(v);
            //verticesToRemove.add(v);
            for (Iterator it2 = p.getTriangles(); it2.hasNext();) {
                //trianglesToAdd.add((Triangle)it2.next());
                graph.addTriangle((Triangle)it2.next());
            }
            //System.out.println("NV: " + graph.getNoOfVertices());
            //it = graph.getVertices();
        }
        
        
        int four = 0;
        int five = 0;
        int six = 0;
        /*
        for (Iterator it = verticesToRemove.iterator(); it.hasNext();) {
            if (graph.removeVertex((Vertex)it.next())) {
                four++;
            }
            six++;
        }
        /*for (Iterator it = trianglesToAdd.iterator(); it.hasNext();) {
            //graph.addTriangle((Triangle)it.next());
            five++;
        }*/        
        System.out.println("Ergebnis one= " + one + " two " + two + " three " + three + " four " + four + " five " + five + " six " + six);
    }    
    
}
