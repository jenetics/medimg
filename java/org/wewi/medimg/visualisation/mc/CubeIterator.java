/*
 * CubeIterator.java
 *
 * Created on 20. März 2002, 19:20
 */

package org.wewi.medimg.visualisation.mc;

import org.wewi.medimg.image.Image;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class CubeIterator implements Iterator {
    protected Image image;
    
    private int nCubes;
    private int currentCube;
    private int maxX, maxY, maxZ;
    private int cubeX = 0;
    private int cubeY = 0;
    private int cubeZ = 0;

    /** Creates new CubeIterator */
    public CubeIterator(Image image) {
        this.image = image;
        maxX = image.getMaxX()-1;
        maxY = image.getMaxY()-1;
        maxZ = image.getMaxZ()-1;
        nCubes = maxX*maxY*maxZ;
        currentCube = 0;
    }

    public boolean hasNext() {
        return currentCube < nCubes-1;
    }
        
    public java.lang.Object next() {
        int cubeIndex = 0;
        int f = 1;

        if (image.getColor(cubeX,   cubeY,   cubeZ)   == f)   cubeIndex |= 1;
        if (image.getColor(cubeX+1, cubeY,   cubeZ)   == f)   cubeIndex |= 2;
        if (image.getColor(cubeX+1, cubeY,   cubeZ+1) == f)   cubeIndex |= 4;
        if (image.getColor(cubeX,   cubeY,   cubeZ+1) == f)   cubeIndex |= 8;
        if (image.getColor(cubeX,   cubeY+1, cubeZ)   == f)   cubeIndex |= 16;
        if (image.getColor(cubeX+1, cubeY+1, cubeZ)   == f)   cubeIndex |= 32;
        if (image.getColor(cubeX+1, cubeY+1, cubeZ+1) == f)   cubeIndex |= 64;
        if (image.getColor(cubeX,   cubeY+1, cubeZ+1) == f)   cubeIndex |= 128;

        Cube cube = new Cube(cubeX, cubeY, cubeZ, cubeIndex);
        cubeX = (cubeX+1)%maxX;
        cubeY = (cubeY+1)%maxY;
        cubeZ = cubeZ+1;
        currentCube++;
        
        return cube;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
}
