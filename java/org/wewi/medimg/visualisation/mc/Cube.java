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
        final float d = gridSize;
        points = new Point[8];
        points[0] = new Point(x,   y+d,  z);
        points[1] = new Point(x+d, y+d,   z);
        points[2] = new Point(x+d, y+d,   z+d);
        points[3] = new Point(x,   y+d,   z+d);
        points[4] = new Point(x,   y, z);
        points[5] = new Point(x+d, y, z);
        points[6] = new Point(x+d, y, z+d);
        points[7] = new Point(x, y, z+d);
        cubeIndex = ci;
    }
    
    Point getPoint(int index) {
        return points[index];
    }

    int getCubeIndex() {
        return cubeIndex;
    }
}
