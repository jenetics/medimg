/*
 * TriangleDecimator.java
 *
 * Created on 12. Juni 2002, 15:41
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Werner Weiser
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class TriangleDecimator {
    private TriangleDecimator component;
    
    public TriangleDecimator() {
        component = null;
    }
    
    public TriangleDecimator(TriangleDecimator component) {
        this.component = component;
    }
    
    public void decimate(Graph graph) {
        if (component == null) {
            return;
        }
        component.decimate(graph);
    }

}

