/*
 * Neighborhood3D6.java
 *
 * Created on 18. Februar 2002, 22:49
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Neighborhood3D6 implements Neighborhood {
    private int maxX;
    private int maxY;
    private int maxZ;

    public Neighborhood3D6(int sizeX, int sizeY, int sizeZ) {
        maxX = sizeX;
        maxY = sizeY;
        maxZ = sizeZ;
    }

    public Iterator getNeighbors(Point p) {
        Point3D p3d = (Point3D)p;
        int x = p3d.getX();
        int y = p3d.getY();
        int z = p3d.getZ();
                      
        PointIterator it = new PointIterator(6);
        if (x-1 >= 0) {
            it.addPoint(new Point3D(x-1, y, z));
        }
        if (y-1 >= 0) {
            it.addPoint(new Point3D(x, y-1, z));
        }
        if (z-1 >= 0) {
            it.addPoint(new Point3D(x, y, z-1));
        }
        if (x+1 < maxX) {
            it.addPoint(new Point3D(x+1, y, z));
        }
        if (y+1 < maxY) {
            it.addPoint(new Point3D(x, y+1, z));
        }
        if (z+1 < maxZ) {
            it.addPoint(new Point3D(x, y, z+1));
        }
        
        return it;
    }
    
}
