/*
 * RawGraphWriter.java
 *
 * Created on 11. Juni 2002, 21:13
 */

package org.wewi.medimg.visualisation.mc;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

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
        
        Triangle t;
        out.writeInt(graph.getNoOfTriangles());
        for (Iterator it = graph.getTriangles(); it.hasNext();) {
            t = (Triangle)it.next();
            writePoint(t.getA(), out);
            writePoint(t.getB(), out);
            writePoint(t.getC(), out);
            writePoint(t.getNormal(), out);
        }
        
        out.close();
    }
    
    private void writePoint(Point p, DataOutputStream out) throws IOException {
        out.writeFloat(p.x);
        out.writeFloat(p.y);
        out.writeFloat(p.z);
    }
    
}
