/*
Line3D is a line segment through 3D space, which can be intersected
with the slicing plane (at z=0) to return a point.
 */
 
package org.wewi.medimg.viewer.image; 

public class Line3D {
    public Vector pos,dir;/*Position and direction vector for line.*/

    public Line3D(Vector p1, Vector p2)/*p1,p2:Endpoints of line. Line goes from p1 to p2.*/ {
        pos = p1;
        dir = p2.minus(p1);
    }

    /*Return if, and where, this line intersects the plane z=0.*/
    public boolean intersect(Vector where) {
        double t;
        if (dir.getZ() == 0.0)
            dir.setZ(0.0000001);
        t = -pos.getZ() / dir.getZ();
        where.copyFrom(pos.plus(dir.scaleBy(t)));
        if (t >= 0 && t <= 1.0)
            return true;
        else
            return false;
    }
}
