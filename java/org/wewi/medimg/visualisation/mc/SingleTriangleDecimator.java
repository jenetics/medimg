/*
 * SingleTriangleDecimator.java
 *
 * Created on 12. Juni 2002, 20:04
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  Franz Wilhelmstötter
 * @author  Werner Weiser
 * @version 0.1
 */
public class SingleTriangleDecimator extends TriangleDecimator {
    private static final int MIN_INCIDENT_TRIANGLES = 2;
    
    /** Creates a new instance of SingleTriangleDecimator */
    public SingleTriangleDecimator() {
    }
    
    public SingleTriangleDecimator(TriangleDecimator component) {
        super(component);
    }
    
    public void decimate(Graph graph) {
        super.decimate(graph);
        
        int ntri = 0;
        Vertex v;
        Triangle t;
        Vector verticesToRemove = new Vector();
        for (Iterator it = graph.getVertices(); it.hasNext();) {
            v = (Vertex)it.next();
            ntri = graph.getNoOfIncidentTriangles(v);
            if (ntri >= MIN_INCIDENT_TRIANGLES) {
                continue;
            }  
            //Die Knoten können nicht gleich entfernt werden,
            //da sonst der Iterator ungültig wird
            verticesToRemove.add(v);
        }
        
        //Das eigentliche Entfernen der Dreiecke
        for (Iterator it = verticesToRemove.iterator(); it.hasNext();) {
            graph.removeVertex((Vertex)it.next());
        }
    }
}
