/*
 * StarshapedPolygon.java
 *
 * Created on 11. Juni 2002, 19:41
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

/**
 *
 * @author Franz Wilhelmst�tter
 * @author Werner Weiser
 * @version 0.1
 */
class StarshapedPolygon {
    
    /** Creates a new instance of StarshapedPolygon */
    public StarshapedPolygon(Edge[] edges, Vertex nucleus) {
        close();
    }
    
    public void triangulate() {
    }
    
    public Iterator getTriangles() {
        return null;
    }
    
    public boolean isClosed() {
        return true;
    }
    
    public void getEigenValues(double[] eigenValues) {
        
    }
    
    private void close() {
    
    }
    
}
