/*
 * CoplanarTriangleDecimator.java
 *
 * Created on 12. Juni 2002, 15:43
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class CoplanarTriangleDecimator extends TriangleDecimator {
    
    private static final double EPSILON = 0.1;
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
        for (Iterator it = graph.getVertices(); it.hasNext();) {
            v = (Vertex)it.next();
            // Ein Punkt mit 2 Dreiecksnachbarn ist jedenfalls ein Punkt der Auﬂenkontur
            // und darf daher nicht entfernt werden
            if (graph.getNoOfIncidentTriangles(v) <= 2) {
                continue;
            }
            p = graph.getPolygon(v);
            if (!p.isClosed()) {
                continue;
            }
            // Eigenwerte nach Grˆﬂe geordnet
            p.getEigenValues(eigenValues);
            similarity = ((eigenValues[2] * eigenValues[2]) /
                          (eigenValues[0] * eigenValues[0] + eigenValues[1] * eigenValues[1]));
            if (similarity >= EPSILON) {
                continue;
            }
            p.triangulate();
            graph.removeVertex(v);
            for (Iterator it2 = p.getTriangles(); it2.hasNext();) {
                graph.addTriangle((Triangle)it2.next());
            }
        }
    }    
    
}
