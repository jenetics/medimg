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
 * @version 0.1
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
