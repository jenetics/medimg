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
 * MarchingCubes.java
 *
 * Created on 20. März 2002, 14:32
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import org.wewi.medimg.image.Image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MarchingCubes {                        
    private Image image;
    private Graph graph;
    private int gridSize;
    private float lower, upper;

    /** Creates new MarchingCubes */
    public MarchingCubes(Image image, int gridSize, float lower, float upper) {
        this.image = image;
        this.gridSize = gridSize;
        this.lower = lower;
        this.upper = upper;
        
        graph = new Graph();
    }
    
    protected Graph decimation(Graph g) {
        return g;
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public Graph march() {
        TriangleCreator tc = new TriangleCreator();
        
        Cube cube;
        for (Iterator cbit = new CubeIterator(image, gridSize, lower, upper); cbit.hasNext();) {
            cube = (Cube)cbit.next();
            for (Iterator it = tc.createTriangles(cube); it.hasNext();) {
                graph.addTriangle((Triangle)it.next());
            }
        }
        
        return decimation(graph);        
    }
    
}





