/*
 * Cube.java
 *
 * Created on 20. März 2002, 19:19
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Cube {
    private final int cubeIndex;
    private final Point origin;

    /** Creates new Cube */
    public Cube(int x, int y, int z, int ci) {
        origin = new Point(x, y, z);
        cubeIndex = ci;
    }
   
    public Point getOrigin() {
        return origin;
    }
    
    public Point getPoint(int index) {
        switch (index) {
            case 0: 
                return origin;
            case 1:
                return new Point(origin.getX()+1, origin.getY(), origin.getZ());
            case 2:
                return new Point(origin.getX()+1, origin.getY(), origin.getZ()+1);
            case 3:
                return new Point(origin.getX(), origin.getY(), origin.getZ()+1);
            case 4:
                return new Point(origin.getX(), origin.getY()+1, origin.getZ());
            case 5:
                return new Point(origin.getX()+1, origin.getY()+1, origin.getZ());
            case 6:
                return new Point(origin.getX()+1, origin.getY()+1, origin.getZ()+1);
            case 7:
                return new Point(origin.getX(), origin.getY()+1, origin.getZ()+1);             
            default: throw new IllegalArgumentException("No such Index: " + index);
        }
    }
    
    public int getCubeIndex() {
        return cubeIndex;
    }
}
