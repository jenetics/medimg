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
final class Cube {
    private final int cubeIndex;
    private final Point[] points;

    /** Creates new Cube */
    Cube(float x, float y, float z, float gridSize, int ci) {
        float d = gridSize;
        points = new Point[8];
        points[0] = new Point(x,   y,   z);
        points[1] = new Point(x+d, y,   z);
        points[2] = new Point(x+d, y,   z+d);
        points[3] = new Point(x,   y,   z+d);
        points[4] = new Point(x,   y+d, z);
        points[5] = new Point(x+d, y+d, z);
        points[6] = new Point(x+d, y+d, z+d);
        points[7] = new Point(x+d, y+d, z+d);
        cubeIndex = ci;
    }
    
    Point getPoint(int index) {
        return points[index];
    }

    int getCubeIndex() {
        return cubeIndex;
    }
}
