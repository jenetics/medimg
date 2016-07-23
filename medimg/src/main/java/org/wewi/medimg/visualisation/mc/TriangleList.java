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
 * TriangleArray.java
 *
 * Created on March 18, 2002, 4:35 PM
 */

package org.wewi.medimg.visualisation.mc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TriangleList {
    private Vector triangles;
    
    /** Creates a new instance of TriangleArray */
    public TriangleList() {
        triangles = new Vector(100);
    }
    
    TriangleList(int size) {
        triangles = new Vector(size);
    }
    
    public void add(Triangle t) {
        triangles.add(t);
    }
    
    public int size() {
        return triangles.size();
    }
    
    public Iterator iterator() {
        return triangles.iterator();
    }
    
    public void write(File file) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        out.writeInt(size());
        Triangle tri;
        for (Iterator it = iterator(); it.hasNext();) {
            tri = (Triangle)it.next();
            out.writeFloat(tri.getA().x); out.writeFloat(tri.getA().y); out.writeFloat(tri.getA().z);
            out.writeFloat(tri.getB().x); out.writeFloat(tri.getB().y); out.writeFloat(tri.getB().z);
            out.writeFloat(tri.getC().x); out.writeFloat(tri.getC().y); out.writeFloat(tri.getC().z);
            out.writeFloat(tri.getNormal().x);
            out.writeFloat(tri.getNormal().y);
            out.writeFloat(tri.getNormal().z);
        }
    }
    
    public static TriangleList read(File file) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int size = in.readInt();
        TriangleList triList = new TriangleList(size);
        float x = 0, y = 0, z = 0;
        Vertex A, B, C;
        Point n;
        for (int i = 0; i < size; i++) {
            x = in.readFloat(); y = in.readFloat(); z = in.readFloat();
            A = new Vertex(x, y, z);
            x = in.readFloat(); y = in.readFloat(); z = in.readFloat();
            B = new Vertex(x, y, z);
            x = in.readFloat(); y = in.readFloat(); z = in.readFloat();
            C = new Vertex(x, y, z);
            x = in.readFloat(); y = in.readFloat(); z = in.readFloat();
            n = new Point(x, y, z);            
            triList.add(new Triangle(A, B, C, n));
        }
        
        return triList;
    }
}
