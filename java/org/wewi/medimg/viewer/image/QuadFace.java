
package org.wewi.medimg.viewer.image; 


final class QuadFace {
    private Vector v[] = new Vector[5];

    public QuadFace(Vector a, Vector b, Vector c, Vector d) {
        v[0] = a;
        v[1] = b;
        v[2] = c;
        v[3] = d;
        v[4] = a;
    }

    /*Return the intersecting line of this face with the plane z=0, or null if they do not intersect.*/
    public Line3D intersect() throws ArithmeticException {
        int nIntersections = 0;

        Vector intersections[] = new Vector[4];
        for (int i = 0; i < 4; i++) {
            intersections[i] = new Vector(0, 0, 0);
        }
        
        for (int i = 0; i < 4; i++) {
            Line3D line = new Line3D(v[i], v[i + 1]);
            if (line.intersect(intersections[nIntersections])) {
                nIntersections++;
            }
        }
        
        if (nIntersections == 0) {
            return (Line3D) null;
        }
        
        Line3D isectLine = new Line3D(intersections[0], intersections[1]);
        if (nIntersections == 2) {
            return isectLine;
        }
        
        //Logic error: the number of point intersections of a plane 
        //and a planar polygon must be either 0 or 2-- nothing else makes sense.
        String err = "ERROR!  " + Integer.toString(nIntersections) +
                     " intersections!  This polygon must not be planar!";
        throw new ArithmeticException(err);
    }
}
