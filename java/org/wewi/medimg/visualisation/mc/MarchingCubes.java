/*
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





