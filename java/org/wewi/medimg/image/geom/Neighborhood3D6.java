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
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    public Neighborhood3D6(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Iterator getNeighbors(Point p) {
        int x = p.getOrdinate(0);
        int y = p.getOrdinate(1);
        int z = p.getOrdinate(2);
                      
        PointIterator it = new PointIterator(6);
        if (x-1 >= minX) {
            it.addPoint(new Point3D(x-1, y, z));
        }
        if (y-1 >= minY) {
            it.addPoint(new Point3D(x, y-1, z));
        }
        if (z-1 >= minZ) {
            it.addPoint(new Point3D(x, y, z-1));
        }
        if (x+1 <= maxX) {
            it.addPoint(new Point3D(x+1, y, z));
        }
        if (y+1 <= maxY) {
            it.addPoint(new Point3D(x, y+1, z));
        }
        if (z+1 <= maxZ) {
            it.addPoint(new Point3D(x, y, z+1));
        }
        
        return it;
    }
    
}
