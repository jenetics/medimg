/*
 * CubeIterator.java
 *
 * Created on 20. März 2002, 19:20
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class CubeIterator implements Iterator {
    private Image image;
    
    private int gridSize = 1;
    private float lower = 50;
    private float upper = 150;
    private int maxX, maxY, maxZ;
    private int cubeX = 0;
    private int cubeY = 0;
    private int cubeZ = 0;
    
    private boolean hasNext = true;

    /** Creates new CubeIterator */
    CubeIterator(Image image) {
        this.image = image;
        maxX = image.getMaxX()-image.getMinX();
        maxY = image.getMaxY()-image.getMinY();
        maxZ = image.getMaxZ()-image.getMinZ();
    }
    
    public CubeIterator(Image image, int gridSize, float lower, float upper) {
        this(image);
        this.gridSize = gridSize;
        this.lower = lower;
        this.upper = upper;
    }

    public boolean hasNext() {
        return hasNext;
    }
        
    public Object next() {
        int cubeIndex = 0;
        final int d = gridSize;
        
        int color = image.getColor(cubeX,   cubeY+d,   cubeZ);
        if (color >= lower && color <= upper)   cubeIndex |= 1;
        
        color = image.getColor(cubeX+d, cubeY+d,   cubeZ);
        if (color >= lower && color <= upper)   cubeIndex |= 2;
        
        color = image.getColor(cubeX+d, cubeY+d,   cubeZ+d);
        if (color >= lower && color <= upper)   cubeIndex |= 4;
        
        color = image.getColor(cubeX,   cubeY+d,   cubeZ+d);
        if (color >= lower && color <= upper)   cubeIndex |= 8;
        
        color = image.getColor(cubeX,   cubeY, cubeZ);
        if (color >= lower && color <= upper)   cubeIndex |= 16;
        
        color = image.getColor(cubeX+d, cubeY, cubeZ);
        if (color >= lower && color <= upper)   cubeIndex |= 32;
        
        color = image.getColor(cubeX+d, cubeY, cubeZ+d);
        if (color >= lower && color <= upper)   cubeIndex |= 64;
        
        color = image.getColor(cubeX,   cubeY, cubeZ+d);
        if (color >= lower && color <= upper)   cubeIndex |= 128;

        Cube cube = new Cube(cubeX, cubeY, cubeZ, gridSize, cubeIndex);
        
        if (cubeX+d < maxX-d) {
            cubeX += d;
        } else {
            cubeX = 0;
            if (cubeY+d < maxY-d) {
                cubeY += d;
            } else {
                cubeY = 0;
                if (cubeZ+d < maxZ-d) {
                    cubeZ += d;
                } else {
                    hasNext = false;
                }
            }
        }

        return cube;
    }
 
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
