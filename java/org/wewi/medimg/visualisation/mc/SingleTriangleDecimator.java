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
