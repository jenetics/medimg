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
 * @version 0.1
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
