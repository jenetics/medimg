/*
 * RawGraphReader.java
 *
 * Created on 11. Juni 2002, 21:15
 */

package org.wewi.medimg.visualisation.mc;

import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class RawGraphReader implements GraphReader {
    private Graph graph;
    
    /** Creates a new instance of RawGraphReader */
    public RawGraphReader() {
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public void read() throws IOException {
    }
    
}
