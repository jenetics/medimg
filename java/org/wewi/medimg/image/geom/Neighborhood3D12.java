/*
 * Neighborhood3D12.java
 *
 * Created on 18. Februar 2002, 22:50
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Neighborhood3D12 implements Neighborhood {
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    /** Creates new Neighborhood3D12 */
    public Neighborhood3D12(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Iterator getNeighbors(Point p) {                                                                                     
        Point3D p3d = (Point3D)p;
        int x = p3d.getX();
        int y = p3d.getY();
        int z = p3d.getZ();
        
        PointIterator it = new PointIterator(12);
        if (x-1 >= minX && z-1 >= minZ) {
            it.addPoint(new Point3D(x-1, y, z-1));
        }
        if (x-1 >= minX && z+1 <= maxZ) {
            it.addPoint(new Point3D(x-1, y, z+1));
        }
        if (x-1 >= minX && y-1 >= minY) {
            it.addPoint(new Point3D(x-1, y-1, z));
        }
        if (y-1 >= minY && z-1 >= minZ) {
            it.addPoint(new Point3D(x, y-1, z-1));
        }
        if (y-1 >= minY && z+1 <= maxZ) {
            it.addPoint(new Point3D(x, y-1, z+1));
        }
        if (x+1 <= maxX && y-1 >= minY) {
            it.addPoint(new Point3D(x+1, y-1, z));
        }
        if (x+1 <= maxX && z+1 <= maxZ) {
            it.addPoint(new Point3D(x+1, y, z+1));
        }
        if (x+1 <= maxX && z-1 >= minZ) {
            it.addPoint(new Point3D(x+1, y, z-1));
        }
        if (x+1 <= maxX && y+1 <= maxY) {
            it.addPoint(new Point3D(x+1, y+1, z));
        }
        if (y+1 <= maxY && z+1 <= maxZ) {
            it.addPoint(new Point3D(x, y+1, z+1));
        }
        if (y+1 <= maxX && z-1 >= minZ) {
            it.addPoint(new Point3D(x, y+1, z-1));
        }
        if (x-1 >= minX && y+1 <= maxY) {
            it.addPoint(new Point3D(x-1, y+1, z));
        }
        
        return it;
    }
    
}
