/*
QuadFace is a quadrilateral face, which can also be
intersected with z=0 to yeild an intersecting line segment (Line3D).
 */
 
package org.wewi.medimg.viewer.image; 


public class QuadFace {
    Vector v[] = new Vector[5];

    public QuadFace(Vector a, Vector b, Vector c, Vector d) {
        v[0] = a;
        v[1] = b;
        v[2] = c;
        v[3] = d;
        v[4] = a;
    }

    /*Return the intersecting line of this face with the plane z=0, or null if they do not intersect.*/
    public Line3D intersect() {
        int nIntersections = 0;
        int i;
        Vector intersections[] = new Vector[4];
        for (i = 0; i < 4; i++)
            intersections[i] = new Vector(0, 0, 0);
        for (i = 0; i < 4; i++) {
            Line3D line = new Line3D(v[i], v[i + 1]);
            if (line.intersect(intersections[nIntersections]))
                nIntersections++;
        }
        if (nIntersections == 0)
            return (Line3D) null;
        Line3D isectLine = new Line3D(intersections[0], intersections[1]);
        if (nIntersections == 2)
            return isectLine;
        /*Logic error: the number of point intersections of a plane and a planar polygon
            must be either 0 or 2-- nothing else makes sense.*/
        System.out.println("ERROR!  " + Integer.toString(nIntersections) +
                " intersections!  This polygon must not be planar!");
        return (Line3D) null;
    }
}
