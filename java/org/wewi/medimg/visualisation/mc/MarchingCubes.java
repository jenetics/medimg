/*
 * MarchingCubes.java
 *
 * Created on 20. März 2002, 14:32
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import org.wewi.medimg.seg.statistic.*;
import java.io.*;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MarchingCubes {                        
    private Image image;
    private CubeIterator cubeIterator;
    private int gridSize;
    private float lower, upper;

    /** Creates new MarchingCubes */
    public MarchingCubes(Image image, int gridSize, float lower, float upper) {
        this.image = image;
        this.gridSize = gridSize;
        this.lower = lower;
        this.upper = upper;
        cubeIterator = new ImageCubeIterator(image, gridSize, lower, upper);
    }
    
    protected TriangleList compactation(TriangleList tl) {
        return tl;
    }
    
    public TriangleList march() {
        TriangleList tl = new TriangleList();
        TriangleFactory tf = new TriangleFactory();
        
        Cube cube;
        while (cubeIterator.hasNext()) {
            cube = cubeIterator.next();
            for (Iterator it = tf.createTriangles(cube); it.hasNext();) {
                tl.add((Triangle)it.next());
            }
        }
        tl = compactation(tl);        
        
        return tl;
    }
    
}





