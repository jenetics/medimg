/*
 * RawGraphWriter.java
 *
 * Created on 11. Juni 2002, 21:13
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import java.io.IOException;
import java.io.File;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class RawGraphWriter implements GraphWriter {
    private Graph graph;
    private File file;
    
    /** Creates a new instance of RawGraphWriter */
    public RawGraphWriter(Graph graph, File file) {
        this.graph = graph;
        this.file = file;
    }
    
    public void write() throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        
        for (Iterator it = graph.getVertices(); it.hasNext();) {
            
        }
    }
    
    private void writePoint(Point p, DataOutputStream out) throws IOException {
        out.writeFloat(p.x);
        out.writeFloat(p.y);
        out.writeFloat(p.z);
    }
    
}
