/*
 * BoundingBox.java
 *
 * Created on 26. März 2002, 10:16
 */

package org.wewi.medimg.reg;

import java.util.Vector;

import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.math.geom.DoubleDataPoint;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class BoundingBox {
    
    double[][] vertices = new double[8][3];
    double[][] normal = new double[6][4];
    double[][] planePoint = new double[6][3];
    boolean[][] ADJ = new boolean[8][8];
    static double EPSILON = 0.01;

 
    /** Creates new BoundingBox */
    public BoundingBox(double[][] bb) {
	// 8 x 3 Matrix 
        firstInit(bb);
	initialize();
    }

    public BoundingBox(BoundingBox bb) {
	double[][] vertices = new double[8][3];
	//bb->getVertices(vertices);
	vertices = bb.getVertices();
	firstInit(vertices);
	initialize();
    }
    
    public void firstInit(double[][] bb) {
	// 8 x 3 Matrix
        int i, j;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			ADJ[i][j] = false;
		}
	}
	ADJ[0][1] = true;
	ADJ[0][3] = true;
	ADJ[0][4] = true;
	ADJ[1][2] = true;
	ADJ[1][5] = true;
	ADJ[2][3] = true;
	ADJ[2][6] = true;
	ADJ[3][7] = true;
	ADJ[4][5] = true;
	ADJ[4][7] = true;
	ADJ[5][6] = true;
	ADJ[6][7] = true;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 3; j++) {
			vertices[i][j] = bb[i][j];
		}
	}
    }    

    public void copyRow(double[][] m1, int row1, double[][] m2, int row2) {
        // Matrix1 = 8x 3; Matrix2 = 24 x 3    
        int j;
        for (j = 0; j < 3; j++) {
            m2[row2][j] = m1[row1][j];
        }
    }

    public void initialize() {
            double[][] planesPEPoints = new double[24][3];

            copyRow(vertices, 0, planesPEPoints, 0);
            copyRow(vertices, 3, planesPEPoints, 1);
            copyRow(vertices, 1, planesPEPoints, 2);
            copyRow(vertices, 6, planesPEPoints, 3);

            copyRow(vertices, 4, planesPEPoints, 4);
            copyRow(vertices, 5, planesPEPoints, 5);
            copyRow(vertices, 7, planesPEPoints, 6);
            copyRow(vertices, 2, planesPEPoints, 7);

            copyRow(vertices, 0, planesPEPoints, 8);
            copyRow(vertices, 4, planesPEPoints, 9);
            copyRow(vertices, 3, planesPEPoints, 10);
            copyRow(vertices, 6, planesPEPoints, 11);

            copyRow(vertices, 1, planesPEPoints, 12);
            copyRow(vertices, 2, planesPEPoints, 13);
            copyRow(vertices, 5, planesPEPoints, 14);
            copyRow(vertices, 7, planesPEPoints, 15);

            copyRow(vertices, 4, planesPEPoints, 16);
            copyRow(vertices, 0, planesPEPoints, 17);
            copyRow(vertices, 5, planesPEPoints, 18);
            copyRow(vertices, 2, planesPEPoints, 19);

            copyRow(vertices, 3, planesPEPoints, 20);
            copyRow(vertices, 7, planesPEPoints, 21);
            copyRow(vertices, 2, planesPEPoints, 22);
            copyRow(vertices, 5, planesPEPoints, 23);


            // Erschaffen der 6 Halbräume vom Parallelepiped
            int i, j;
            double[] a = new double[3];
            double[] b = new double[3];
            double[] c = new double[3];
            double[] n = new double[3];
            double tmp1, tmp2;

            for (i = 0; i < 6; i++) {
                    a[0] = planesPEPoints[i*4][0];
                    a[1] = planesPEPoints[i*4][1];
                    a[2] = planesPEPoints[i*4][2];

                    b[0] = planesPEPoints[i*4+1][0] - planesPEPoints[i*4][0];
                    b[1] = planesPEPoints[i*4+1][1] - planesPEPoints[i*4][1];
                    b[2] = planesPEPoints[i*4+1][2] - planesPEPoints[i*4][2];

                    c[0] = planesPEPoints[i*4+2][0] - planesPEPoints[i*4][0];
                    c[1] = planesPEPoints[i*4+2][1] - planesPEPoints[i*4][1];
                    c[2] = planesPEPoints[i*4+2][2] - planesPEPoints[i*4][2];

                    //Kreuzprodukt
                    n[0] = b[1] * c[2] - b[2] * c[1];
                    n[1] = b[2] * c[0] - b[0] * c[2];
                    n[2] = b[0] * c[1] - b[1] * c[0];


                    tmp2 = Math.sqrt(Math.pow(n[0], 2) + Math.pow(n[1], 2) + Math.pow(n[2], 2));
                    if (tmp2 > EPSILON) {
                            n[0] = n[0] / tmp2;
                            n[1] = n[1] / tmp2;
                            n[2] = n[2] / tmp2;
                    }
                    // Ebenengleichung berechnen
                    tmp1 = n[0] * a[0] + n[1] * a[1] + n[2] * a[2];
                    // Richtung berechnen
                    tmp2 = n[0] * planesPEPoints[i*4+3][0] + 
                               n[1] * planesPEPoints[i*4+3][1] + 
                               n[2] * planesPEPoints[i*4+3][2];
                    if (tmp2 < tmp1) {
                            // Normalvektor umdrehen
                            n[0] = -n[0];
                            n[1] = -n[1];
                            n[2] = -n[2];
                            tmp1 = -tmp1;
                    }
                    for (j = 0; j < 3; j++) {
                            normal[i][j] = n[j];
                            planePoint[i][j] = a[j];
                    }
                    normal[i][3] = tmp1;
            }
    }    

    public void transform(Transformation trans) {
            int i, j;
            double[] s = new double[3];
            double[] t = new double[3];
            for (i = 0; i < 8; i++) {
                    for (j = 0; j < 3; j++) {
                            s[j] = vertices[i][j];
                    }
                    trans.transform(s, t);
                    for (j = 0; j < 3; j++) {
                            vertices[i][j] = t[j];
                    }		
            }
            initialize();
    }

    public double[][] getVertices() {
            /*int i, j;
            double[][] v = new double[8][3];
            for (i = 0; i < 8; i++) {
                    for (j = 0; j < 3; j++) {
                            v[i][j] = vertices[i][j];
                    }
            }
            return v;*/
        return vertices;
    }

    public double[][] getNormal() {
            /*int i, j;
            for (i = 0; i < 6; i++) {
                    for (j = 0; j < 4; j++) {
                            n[i][j] = normal[i][j];
                    }
            }*/
        return normal;
    }

    public double[][] getPlanePoint() {
            /*int i, j;
            for (i = 0; i < 6; i++) {
                    for (j = 0; j < 3; j++) {
                            pp[i][j] = planePoint[i][j];
                    }
            }*/
        return planePoint;
    }
    
    public boolean[][] getADJ() {
            /*int i, j;
            for (i = 0; i < 8; i++) {
                    for (j = 0; j < 8; j++) {
                            adj[i][j] = ADJ[i][j];
                    }
            }*/
        return ADJ;
    }

    public double getVolume() {

            //int *field = NULL;
            int[] field;
            int i, j, k, l, tmp;
            //double* end;
            double[] end = new double[3];
            double tmp1, tmp2;
            double volume = 0.0;
            double[][] matDet = new double[4][4];
            double[][] triangle = new double[3][3];
            //std::vector<double*> surfacePoints[6];
            //std::vector<double*> pointorder;
            Vector pointorder = new Vector();
            Vector[] surfacePoints = new Vector[6];
            for ( i = 0; i < surfacePoints.length; i++) {
                surfacePoints[i] = new Vector();
            }

            for ( i = 0; i < 8; i++) {
                    for ( j = 0; j < 6; j++) {
                            // n * x
                            tmp1 = normal[j][0] * vertices[i][0] + 
                                       normal[j][1] * vertices[i][1] + 
                                       normal[j][2] * vertices[i][2];
                            // n * x - d = 0
                            if (Math.abs(tmp1 - normal[j][3]) < EPSILON) {
                                    end = new double[3];
                                    end[0] = vertices[i][0];
                                    end[1] = vertices[i][1];
                                    end[2] = vertices[i][2];
                                    surfacePoints[j].addElement(new DoubleDataPoint(end));
                            }
                    }
            }
            //Volume
            for ( i = 0; i < 6; i++) {
                    // if plain contains more than two points
                    if (surfacePoints[i].size() > 2) {
                            field = new int[surfacePoints[i].size()];
                            for ( j = 0; j < surfacePoints[i].size(); j++) {
                                    field[j] = j;
                            }
                            // Bubble sort
                            for (j = surfacePoints[i].size() - 2; j >= 0; j--) {
                                    for ( k = 1; k <= j; k++) {
                                            // generate plain with three points
                                            // 1. point = starting point
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[0][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(0)).getValue(l);
                                            }
                                            matDet[0][3] = 1.0;
                                            // 2. point = starting point + normalvector
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[1][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(0)).getValue(l) + vertices[i][l];
                                            }
                                            matDet[1][3] = 1.0;
                                            // 3. point
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[2][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(field[k])).getValue(l);
                                            }
                                            matDet[2][3] = 1.0;
                                            // point to check
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[3][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(field[k + 1])).getValue(l);
                                            }
                                            matDet[3][3] = 1.0;
                                            //  compute determinante 
                                            tmp1 = -matDet[0][0] * matDet[1][2] * matDet[2][1] + matDet[0][0] * matDet[1][1] * matDet[2][2] + 
                                            matDet[1][2] * matDet[2][1] * matDet[3][0] - matDet[1][1] * matDet[2][2] * matDet[3][0] + 
                                            matDet[0][0] * matDet[1][2] * matDet[3][1] - matDet[1][2] * matDet[2][0] * matDet[3][1] - 
                                            matDet[0][0] * matDet[2][2] * matDet[3][1] + matDet[1][0] * matDet[2][2] * matDet[3][1] + 
                                            matDet[0][2] * (matDet[1][0] * matDet[2][1] - matDet[2][1] * matDet[3][0] + matDet[1][1] * 
                                            (-matDet[2][0] + matDet[3][0]) - matDet[1][0] * matDet[3][1] + matDet[2][0] * matDet[3][1]) +
                                            (-matDet[0][0] * matDet[1][1] + matDet[1][1] * matDet[2][0] + matDet[0][0] * matDet[2][1] - 
                                            matDet[1][0] * matDet[2][1]) * matDet[3][2] + matDet[0][1] * (matDet[1][2] * matDet[2][0] - 
                                            matDet[1][0] * matDet[2][2] - matDet[1][2] * matDet[3][0] + matDet[2][2] * matDet[3][0] + 
                                            matDet[1][0] * matDet[3][2] - matDet[2][0] * matDet[3][2]);
                                            // if k. element is right of k+1. element -> change them
                                            if ( tmp1 > EPSILON) {
                                              tmp = field[k];
                                              field[k] = field[k+1];
                                              field[k+1] = tmp;
                                            }
                                    }
                            }
                            // ccw ordered surface points 
                            pointorder.clear();
                            for ( j = 0; j < surfacePoints[i].size(); j++) {
                                    pointorder.addElement(surfacePoints[i].elementAt(field[j]));
                            }
                            if (field != null) {
                                    field = null;
                            }
                            surfacePoints[i] = pointorder;
                            //triangulate
                            for ( j = 0; j < surfacePoints[i].size() - 2; j++) {
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[0][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(surfacePoints[i].size() - 1)).getValue(l);
                                    }
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[1][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(j)).getValue(l);
                                    }
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[2][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(j + 1)).getValue(l);
                                    }
                                    // add to volume
                                    // V(dreieck) = (1/6) (z0 + z1 + z2) [x0(y1 - y2) + x1( y2 - y0) + x2(y0 -y1)]
                                    tmp2 = (1.0 / 6.0) 
                                            * (triangle[0][2] + triangle[1][2] + triangle[2][2])
                                            * (triangle[0][0] * (triangle[1][1] - triangle[2][1])
                                            + triangle[1][0] * (triangle[2][1] - triangle[0][1])
                                            + triangle[2][0] * (triangle[0][1] - triangle[1][1]));
                                    volume += tmp2;
                            }
                    }
            }
            return volume;
    } 
    
    public double getIntersectingVolume(BoundingBox bb) {

            int[] field;
            int i, j, k, l, tmp;
            double[] end = new double[3];
            double[] erg = new double[3];
            double[] plane = new double[3];
            double tmp1, tmp2, alpha;
            double volume = 0.0;
            double[][] matDet = new double[4][4];
            double[][] triangle = new double[3][3];
            Vector pointorder = new Vector();
            Vector[] surfacePoints = new Vector[12];            
            for ( i = 0; i < surfacePoints.length; i++) {
                surfacePoints[i] = new Vector();
            }
            Vector intersectingPoints = new Vector();
            Vector allplanes = new Vector();            
            //std::vector<double*>::iterator position;
            double[][] srcVertices = new double[8][3];
            double[][] srcPlanes = new double[6][4];
            double[][] srcPlanePoints = new double[6][3];            
            srcVertices = bb.getVertices();
            srcPlanes = bb.getNormal();
            srcPlanePoints = bb.getPlanePoint();
            for (i = 0; i < 8; i++) {
                    // generate all intersecting points between parallelepiped and source cuboid
                    // take a point
                    for (j = 0; j < 8; j++) {
                            // search a neighbour
                            if (ADJ[i][j]) {
                                    // g := vec(a) + t vec(b)
                                    end[0] = 0; end[1] = 0; end[2] = 0;
                                    end[0] =  srcVertices[j][0] - srcVertices[i][0];
                                    end[1] =  srcVertices[j][1] - srcVertices[i][1];
                                    end[2] =  srcVertices[j][2] - srcVertices[i][2];
                                    // intersect with all 6 halfspaces
                                    // E := A + B + C = D
                                    for  (k = 0; k < 6; k++) {
                                            // tmp1 = vec(n) * vec(a)
                                            tmp1 = normal[k][0] * srcVertices[j][0] + 
                                                       normal[k][1] * srcVertices[j][1] + 
                                                       normal[k][2] * srcVertices[j][2];
                                            // tmp2 = vec(n) * vec(b)
                                            tmp2 = normal[k][0] * end[0] + 
                                                       normal[k][1] * end[1] + 
                                                       normal[k][2] * end[2];
                                            // if plain and g are not parallel
                                            if (tmp2 != 0) {
                                                    // t0 = (plain(D) - vec(n) * vec(a) ) / vec(n) * vec(b)
                                                    alpha = (normal[k][3] - tmp1) / tmp2;
                                                    // x0 = vec(a) + t0 * vec(b); intersecting point
                                                    erg = new double[3];
                                                    erg[0] =  alpha * end[0] + srcVertices[j][0];
                                                    erg[1] =  alpha * end[1] + srcVertices[j][1];
                                                    erg[2] =  alpha * end[2] + srcVertices[j][2];
                                                    intersectingPoints.addElement(new DoubleDataPoint(erg));
                                            }
                                    }
                            }
                    }
                    // plus vertices of the source cuboid
                    erg = new double[3];
                    erg[0] =  vertices[i][0];
                    erg[1] =  vertices[i][1];
                    erg[2] =  vertices[i][2];
                    intersectingPoints.addElement(new DoubleDataPoint(erg));
            }

            // delete equal points

            for ( i = 0; i < intersectingPoints.size(); i++) {
                    for ( j = i + 1; j < intersectingPoints.size(); j++) {
                            if ((Math.abs(((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(0) - ((DoubleDataPoint)intersectingPoints.elementAt(j)).getValue(0)) < EPSILON) && 
                                    (Math.abs(((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(1) - ((DoubleDataPoint)intersectingPoints.elementAt(j)).getValue(1)) < EPSILON) && 
                                    (Math.abs(((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(2) - ((DoubleDataPoint)intersectingPoints.elementAt(j)).getValue(2)) < EPSILON)) {
                                    intersectingPoints.remove(j);
                                    /*end[0] = 0; end[1] = 0; end[2] = 0;
                                    end = (DoubleDataPoint)intersectingPoints.elementAt(j);
                                    position = std::remove(intersectingPoints.begin(), intersectingPoints.end(), end);
                                    intersectingPoints.erase(position, intersectingPoints.end());			
                                    //delete [] *position;*/
                                    j--;
                            }
                    }
            }

            //delete all points that are not contained by the cuboid
            boolean control = true;
            // take point
            for ( i = 0; i < intersectingPoints.size(); i++) {
                    // take halfspace
                    for (j = 0; j < 6; j++) {
                            // tmp1 = vec(n) * vec(point) 
                            tmp1 = normal[j][0] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(0) + 
                                       normal[j][1] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(1) + 
                                       normal[j][2] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(2);
                            // tmp2 = vec(n) * vec(point_on_plain)
                            tmp2 = normal[j][0] * planePoint[j][0] + 
                                       normal[j][1] * planePoint[j][1] + 
                                       normal[j][2] * planePoint[j][2];
                            // if vec(n) * vec(point) - vec(n) * vec(point_on_plain) < 0 
                            // the point isn't in the direction of the normalvector
                            if ((tmp1 - tmp2) < -EPSILON) {
                                    control = false;
                                    break;
                            }
                    }
                    if (!(control)) {
                            // delete point
                            /*end[0] = 0; 
                            end[1] = 0; 
                            end[2] = 0;
                            end = intersectingPoints[i];
                            position = std::remove(intersectingPoints.begin(), intersectingPoints.end(), end);
                            intersectingPoints.erase(position, intersectingPoints.end());*/
                            intersectingPoints.remove(i);
                            //delete [] *position;
                            control = true;
                            i--;
                    }
            }
            //delete all points that are not contained by the parallelepiped
            control = true;
            // take point
            for ( i = 0; i < intersectingPoints.size(); i++) {
                    // take halfspace
                    for (j = 0; j < 6; j++) {
                            // tmp1 = vec(n) * vec(point) 
                            tmp1 = srcPlanes[j][0] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(0) + 
                                       srcPlanes[j][1] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(1) + 
                                       srcPlanes[j][2] * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(2);
                            // tmp2 = vec(n) * vec(point_on_plain)
                            tmp2 = srcPlanes[j][0] * srcPlanePoints[j][0] + 
                                       srcPlanes[j][1] * srcPlanePoints[j][1] + 
                                       srcPlanes[j][2] * srcPlanePoints[j][2];
                            // if vec(n) * vec(point) - vec(n) * vec(point_on_plain) < 0 
                            // the point isn't in the direction of the normalvector
                            if ((tmp1 - tmp2) < -EPSILON) {
                                    control = false;
                                    break;
                            }
                    }
                    if (!(control)) {
                            // delete point
                            /*end[0] = 0; end[1] = 0; end[2] = 0;
                            end = intersectingPoints[i];
                            position = std::remove(intersectingPoints.begin(), intersectingPoints.end(), end);
                            intersectingPoints.erase(position, intersectingPoints.end());*/
                            intersectingPoints.remove(i);
                            //delete [] *position;
                            control = true;
                            i--;
                    }
            }

            // generate all plains
            // avoid equal plains  and plains containing only zero from the cuboid
            for ( i = 0; i < 6; i++) {
                    control = false;
                    for ( j = 0; j < 4 ; j++) {
                            if (normal[i][j] != 0.0) {
                                    control = true;
                                    break;
                            }
                    }
                    if (control) {
                            for ( k = 0; k < allplanes.size(); k++) {
                                    for ( j = 0; j < 4 ; j++) {
                                            if (Math.abs(normal[i][j] - ((DoubleDataPoint)allplanes.elementAt(k)).getValue(j)) > EPSILON) {
                                                    control = true;
                                                    break;
                                            }
                                            control = false;
                                    }
                                    if (!control) {
                                            break;
                                    }
                            }
                            if (control) {
                                    plane = new double[4];
                                    plane[0] = normal[i][0];
                                    plane[1] = normal[i][1];
                                    plane[2] = normal[i][2];
                                    plane[3] = normal[i][3];
                                    allplanes.addElement(new DoubleDataPoint(plane));
                            }
                    }
            }
            // generate all plains
            // avoid equal plains  and plains containing only zero from the parallelepiped
            for ( i = 0; i < 6; i++) {
                    control = false;
                    for ( j = 0; j < 4 ; j++) {
                            if (srcPlanes[i][j] != 0.0) {
                                    control = true;
                                    break;
                            }
                    }
                    if (control) {
                            for ( k = 0; k < allplanes.size(); k++) {
                                    for ( j = 0; j < 4 ; j++) {
                                            if (Math.abs(srcPlanes[i][j] - ((DoubleDataPoint)allplanes.elementAt(k)).getValue(j)) > EPSILON) {
                                                    control = true;
                                                    break;
                                            }
                                            control = false;
                                    }
                                    if (!control) {
                                            break;
                                    }
                            }
                            if (control) {
                                    plane = new double[4];
                                    plane[0] = srcPlanes[i][0];
                                    plane[1] = srcPlanes[i][1];
                                    plane[2] = srcPlanes[i][2];
                                    plane[3] = srcPlanes[i][3];
                                    allplanes.addElement(new DoubleDataPoint(plane));
                            }
                    }
            }
            // assign points to the plains
            for ( i = 0; i < intersectingPoints.size(); i++) {
                    // take halfspace
                    for ( j = 0; j < allplanes.size(); j++) {
                            // tmp1 = vec(n) * vec(point) 
                            tmp1 = ((DoubleDataPoint)allplanes.elementAt(j)).getValue(0) * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(0) + 
                                       ((DoubleDataPoint)allplanes.elementAt(j)).getValue(1) * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(1) + 
                                       ((DoubleDataPoint)allplanes.elementAt(j)).getValue(2) * ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(2);
                            // if vec(n) * vec(point)  -  plain(D) = 0
                            // point is member of this plain
                            if (Math.abs(tmp1 - ((DoubleDataPoint)allplanes.elementAt(j)).getValue(3)) < EPSILON) {
                                    erg = new double[3];
                                    erg[0] = ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(0);
                                    erg[1] = ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(1);
                                    erg[2] = ((DoubleDataPoint)intersectingPoints.elementAt(i)).getValue(2);
                                    surfacePoints[j].addElement(new DoubleDataPoint(erg));
                            }
                    }
            }
            //Volume
            for ( i = 0; i < allplanes.size(); i++) {
                    // if plain contains more than two points
                    if (surfacePoints[i].size() > 2) {
                            field = new int[surfacePoints[i].size()];
                            for ( j = 0; j < surfacePoints[i].size(); j++) {
                                    field[j] = j;
                            }
                            // Bubble sort
                            for (j = surfacePoints[i].size() - 2; j >= 0; j--) {
                                    for ( k = 1; k <= j; k++) {
                                            // generate plain with three points
                                            // 1. point = starting point
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[0][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(0)).getValue(l);
                                            }
                                            matDet[0][3] = 1.0;
                                            // 2. point = starting point + normalvector
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[1][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(0)).getValue(l) + ((DoubleDataPoint)allplanes.elementAt(i)).getValue(l);
                                            }
                                            matDet[1][3] = 1.0;
                                            // 3. point
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[2][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(field[k])).getValue(l);
                                            }
                                            matDet[2][3] = 1.0;
                                            // point to check 
                                            for ( l = 0; l < 3; l++) {
                                                    matDet[3][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(field[k + 1])).getValue(l); 
                                            }
                                            matDet[3][3] = 1.0;
                                            // compute determinante 
                                            tmp1 = -matDet[0][0] * matDet[1][2] * matDet[2][1] + matDet[0][0] * matDet[1][1] * matDet[2][2] + 
                                            matDet[1][2] * matDet[2][1] * matDet[3][0] - matDet[1][1] * matDet[2][2] * matDet[3][0] + 
                                            matDet[0][0] * matDet[1][2] * matDet[3][1] - matDet[1][2] * matDet[2][0] * matDet[3][1] - 
                                            matDet[0][0] * matDet[2][2] * matDet[3][1] + matDet[1][0] * matDet[2][2] * matDet[3][1] + 
                                            matDet[0][2] * (matDet[1][0] * matDet[2][1] - matDet[2][1] * matDet[3][0] + matDet[1][1] * 
                                            (-matDet[2][0] + matDet[3][0]) - matDet[1][0] * matDet[3][1] + matDet[2][0] * matDet[3][1]) +
                                            (-matDet[0][0] * matDet[1][1] + matDet[1][1] * matDet[2][0] + matDet[0][0] * matDet[2][1] - 
                                            matDet[1][0] * matDet[2][1]) * matDet[3][2] + matDet[0][1] * (matDet[1][2] * matDet[2][0] - 
                                            matDet[1][0] * matDet[2][2] - matDet[1][2] * matDet[3][0] + matDet[2][2] * matDet[3][0] + 
                                            matDet[1][0] * matDet[3][2] - matDet[2][0] * matDet[3][2]);
                                            // if k. element is right of k+1. element -> change them
                                            if ( tmp1 > EPSILON) {
                                              tmp = field[k];
                                              field[k] = field[k+1];
                                              field[k+1] = tmp;
                                            }
                                    }
                            }

                            // ccw ordered surface points 
                            pointorder.clear();
                            for ( j = 0; j < surfacePoints[i].size(); j++) {
                                    pointorder.addElement(surfacePoints[i].elementAt(field[j]));
                            }
                            if (field != null) {
                                    field = null;
                            }
                            surfacePoints[i] = pointorder;
                            //triangulate
                            for ( j = 0; j < surfacePoints[i].size() - 2; j++) {
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[0][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(surfacePoints[i].size() - 1)).getValue(l);
                                    }
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[1][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(j)).getValue(l);
                                    }
                                    for ( l = 0; l < 3; l++) {
                                                    triangle[2][l] = ((DoubleDataPoint)surfacePoints[i].elementAt(j + 1)).getValue(l);
                                    }
                                    // add to volume
                                    // V(triangle) = (1/6) (z0 + z1 + z2) [x0(y1 - y2) + x1( y2 - y0) + x2(y0 -y1)]
                                    tmp2 = (1.0 / 6.0) 
                                            * (triangle[0][2] + triangle[1][2] + triangle[2][2])
                                            * (triangle[0][0] * (triangle[1][1] - triangle[2][1])
                                            + triangle[1][0] * (triangle[2][1] - triangle[0][1])
                                            + triangle[2][0] * (triangle[0][1] - triangle[1][1]));
                                    volume += tmp2;
                            }
                    }
            }
            return volume;
    }    
}
