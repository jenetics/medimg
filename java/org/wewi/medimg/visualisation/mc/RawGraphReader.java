/*
 * RawGraphReader.java
 *
 * Created on 11. Juni 2002, 21:15
 */

package org.wewi.medimg.visualisation.mc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class RawGraphReader implements GraphReader {
    private Graph graph;
    private File file;
    
    /** Creates a new instance of RawGraphReader */
    public RawGraphReader(File file) {
        this.file = file;
        
        graph = new Graph();
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public void read() throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        
        int size = in.readInt();
        Vertex a, b, c;
        Point normal;
        
        for (int i = 0; i < size; i++) {
            a = readVertex(in);
            b = readVertex(in);
            c = readVertex(in);
            normal = readPoint(in);
            graph.addTriangle(new Triangle(a, b, c, normal));
        }
        
        in.close();
    }
    
    private Point readPoint(DataInputStream in) throws IOException {
        float x = in.readFloat();
        float y = in.readFloat();
        float z = in.readFloat();
        return new Point(x, y, z);
    }
    
    private Vertex readVertex(DataInputStream in) throws IOException {
        float x = in.readFloat();
        float y = in.readFloat();
        float z = in.readFloat();
        return new Vertex(x, y, z);
    }    
    
}
