/*
 * FlatGraphWriter.java
 *
 * Created on 12. Juni 2002, 21:18
 */

package org.wewi.medimg.visualisation.mc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @author  Werner Weiser
 * @version 0.1
 */
public class FlatGraphWriter implements GraphWriter {
    private Graph graph;
    private File file;
    
    /** Creates a new instance of FlatGraphWriter */
    public FlatGraphWriter(Graph graph, File file) throws IllegalArgumentException {
        if (graph == null || file == null) {
            throw new IllegalArgumentException("Arguments can't be NULL");
        }
        this.graph = graph;
        this.file = file;
    }
    
    public void write() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        
        Triangle t;
        for (Iterator it = graph.getTriangles(); it.hasNext();) {
            t = (Triangle)it.next();
            writer.print(t.a.toString());
            writer.print(t.b.toString());
            writer.print(t.c.toString());
            writer.println(t.normal.toString());
        }
        
        writer.close();
    }
    
}
