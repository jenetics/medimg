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


package org.wewi.medimg.viewer.image; 

final class Line3D {
    private Vector position;
    private Vector direction;

    public Line3D(Vector p1, Vector p2)/*p1,p2:Endpoints of line. Line goes from p1 to p2.*/ {
        setPosition(p1);
        setDirection(p2.minus(p1));
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Return if, and where, this line intersects the plane z=0.
     * 
     * @param where
     * @return boolean
     */
    public boolean intersect(Vector where) {
        double t;
        if (direction.getZ() == 0.0) {
            direction.setZ(0.0000001);
        }
        
        t = -position.getZ() / direction.getZ();
        where.copyFrom(position.plus(direction.scaleBy(t)));
        
        if (t >= 0 && t <= 1.0) {
            return true;
        } else {
            return false;
        }
    }
}
