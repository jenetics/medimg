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

/* 
 * Code adapted from Graphics Gems.
 * 
 * unmatrix.c - given a 4x4 matrix, decompose it into standard operations.
 *
 * Author of original code:  Spencer W. Thomas
 *                               University of Michigan
 * 
 * unmatrix - Decompose a non-degenerate 4x4 transformation matrix into
 * the sequence of transformations that produced it.
 * [Sx][Sy][Sz][Shearx/y][Sx/z][Sz/y][Rx][Ry][Rz][Tx][Ty][Tz][P(x,y,z,w)]
 *
 * The coefficient of each transformation is returned in the corresponding
 * element of the vector tran.
 */


/**
 * UnMatrix.java
 * 
 * Created on 19.03.2003, 17:04:41
 *
 */
package org.wewi.medimg.image.geom.transform;

import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnMatrix {
    private static final int U_SCALEX = 0;
    private static final int U_SCALEY = 1;
    private static final int U_SCALEZ = 2;
    private static final int U_SHEARXY = 3;
    private static final int U_SHEARXZ = 4;
    private static final int U_SHEARYZ = 5;
    private static final int U_ROTATEX = 6;
    private static final int U_ROTATEY = 7;
    private static final int U_ROTATEZ = 8;
    private static final int U_TRANSX = 9;
    private static final int U_TRANSY = 10;
    private static final int U_TRANSZ = 11;
    private static final int U_PERSPX = 12;
    private static final int U_PERSPY = 13;
    private static final int U_PERSPZ = 14;
    private static final int U_PERSPW = 15;
    private double[] tran = new double[16];
        

    /**
     * Constructor for UnMatrix.
     */
    public UnMatrix(Matrix4d matrix) {
        unmatrix(new Matrix4d(matrix));
    }
    
    public UnMatrix(double[] matrix) {
        unmatrix(new Matrix4d(matrix));
    }
    
    private UnMatrix() {
    }
    
    private void unmatrix(Matrix4d matrix) {           
        //System.out.println("drin" + matrix);
        if (matrix.determinant() == 0.0) {
            System.err.println("Singulär B");
            return; //Singuläre Matrix kann nicht behandelt werden.            
        }
		//Normalizing the matrix
        matrix.mul(1.0/matrix.getElement(3, 3));
        
        //pmat wird zum Lösen des perspektivischen Anteils verwendet.
        //Es wird hier automatisch die ober 3x3 Komponente
        //auf Singularität getestet.
        Matrix4d pmat = new Matrix4d(matrix);        

        
        //First, isolate perspective.  This is the messiest.
        Vector4d prsh = new Vector4d();
        if (matrix.getElement(3, 0) != 0 || matrix.getElement(3, 1) != 0 || matrix.getElement(3, 2) != 0) {
            //prhs ist die rechte Seite der Gleichung
            matrix.getRow(3, prsh);

            //Lösen der Gleichung durch Invertieren von pmat 
            //und multiplizieren mit prsh
            Matrix4d invpmat = new Matrix4d(pmat);
            invpmat.invert();
            invpmat.transpose();
            Vector4d psol = (Vector4d)prsh.clone();
            invpmat.transform(psol);
            
     
            //Speichern der Lösung
            double[] temp = new double[4];
            psol.get(temp);
            tran[U_PERSPX] = temp[0];
            tran[U_PERSPY] = temp[1];
            tran[U_PERSPZ] = temp[2];
            tran[U_PERSPW] = temp[3];

            //Entfernen des perspektivischen Anteils
            matrix.setRow(3, 0, 0, 0, 1);
        } else {
            tran[U_PERSPX] = tran[U_PERSPY] = tran[U_PERSPZ] = tran[U_PERSPW] = 0;
        }
        
        //Finden der Translation und entfernen der Translation
        double[] temp = new double[4];
        matrix.getColumn(3, temp);
        tran[U_TRANSX] = temp[0];
        tran[U_TRANSY] = temp[1];
        tran[U_TRANSZ] = temp[2];
        matrix.setColumn(3, 0, 0, 0, 1);


        //Finden der Skalierung und Scherung
        Matrix3d rot = new Matrix3d(matrix.m00, matrix.m01, matrix.m02,
                                    matrix.m10, matrix.m11, matrix.m12,
                                    matrix.m20, matrix.m21, matrix.m22);
                                    
        Vector3d a = new Vector3d();
        Vector3d b = new Vector3d();
        Vector3d c = new Vector3d();                                    

        //Berechnen der X-Skalierung und normalisieren der ersten Spalte
        rot.getColumn(0, a);
        tran[U_SCALEX] = a.length();
        a.normalize();
        rot.setColumn(0, a);
              
        //Berechnen der XY-Scherung und orthogonalisieruen der ersten 
        //und zweiten Spalte der rot-Matrix.
        rot.getColumn(0, a);
        rot.getColumn(1, b);
        tran[U_SHEARXY] = a.dot(b);
        a.scale(-tran[U_SHEARXY]);
        b.add(a);
        rot.setColumn(1, b);
           
    
        //Berechnen der Y-Skalierung und normalisieren der zweiten Spalte
        tran[U_SCALEY] = b.length();
        b.scale(1.0/tran[U_SCALEY]);
        rot.setColumn(1, b);
        tran[U_SHEARXY] /= tran[U_SCALEY];
               
    
        //Berechnen der XZ und YZ-Scherungen und orthogonalisieren der dritten Spalte
        rot.getColumn(0, a);
        rot.getColumn(2, c);
        tran[U_SHEARXZ] = a.dot(c);
        a.scale(-tran[U_SHEARXZ]);
        c.add(a);
        rot.setColumn(2, c);
        rot.getColumn(1, b);
        tran[U_SHEARYZ] = b.dot(c);
        b.scale(-tran[U_SHEARYZ]);
        c.add(b);
        rot.setColumn(2, c);              
   
    
        //Berechnen der Z-Skalierung und normalisieren der dritten Spalte.
        rot.getColumn(2, c);
        tran[U_SCALEZ] = c.length();
        c.scale(1.0/tran[U_SCALEZ]);
        rot.setColumn(2, c);
        tran[U_SHEARXZ] /= tran[U_SCALEZ];
        tran[U_SHEARYZ] /= tran[U_SCALEZ];
                          
                          
                          
        /* At this point, the matrix (in rows[]) is orthonormal.
         * Check for a coordinate system flip.  If the determinant
         * is -1, then negate the matrix and the scaling factors.
         */

        double det = rot.determinant();
        if (det < 0) {
            tran[U_SCALEX] = -tran[U_SCALEX];
            tran[U_SCALEY] = -tran[U_SCALEY];
            tran[U_SCALEZ] = -tran[U_SCALEZ];
            rot.mul(-1);
        } 
        
        
        tran[U_ROTATEY] = Math.asin(rot.getElement(0, 2));
        if (Math.cos(tran[U_ROTATEY]) != 0) {
            tran[U_ROTATEX] = Math.atan(-rot.getElement(1, 2)/rot.getElement(2, 2));
            tran[U_ROTATEZ] = Math.atan(-rot.getElement(0, 1)/rot.getElement(0, 0));
        } else {
            tran[U_ROTATEX] = 0;
            tran[U_ROTATEZ] = Math.atan(-rot.getElement(1, 0));               
        }
        
                          
        double[] da = new double[2];
        double[] db = new double[2];
        double[] dc = new double[2];
        
        da[0] = tran[U_ROTATEX];
        da[1] = Math.PI + da[0];
        db[0] = tran[U_ROTATEY];
        db[1] = Math.PI - db[0];
        dc[0] = tran[U_ROTATEZ];
        dc[1] = Math.PI + dc[0];
        
        loop:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if (testAngles(da[i], db[j], dc[k], rot, 0.1)) {
                        tran[U_ROTATEX] = da[i]; 
                        tran[U_ROTATEY] = db[j];
                        tran[U_ROTATEZ] = dc[k]; 
                        break loop;  
                    }    
                }    
            }    
        }
                       
    }
    
    private boolean testAngles(double a, double b, double c, Matrix3d rot, double EPSILON) {
        double[][] m = new double[3][3];
        m[0][0] = Math.cos(b)*Math.cos(c);
        m[0][1] = -Math.cos(b)*Math.sin(c);
        m[0][2] = Math.sin(b);
        m[1][0] = Math.cos(c)*Math.sin(a)*Math.sin(b) +
               Math.cos(a)*Math.sin(c);
        m[1][1] = -Math.sin(a)*Math.sin(b)*Math.sin(c) +
               Math.cos(a)*Math.cos(c);
        m[1][2] = -Math.cos(b)*Math.sin(a);
        m[2][0] = -Math.cos(a)*Math.cos(c)*Math.sin(b) +
               Math.sin(a)*Math.sin(c);
        m[2][1] = Math.cos(a)*Math.sin(b)*Math.sin(c) +
               Math.cos(c)*Math.sin(a);
        m[2][2] = Math.cos(a)*Math.cos(b); 
        
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(m[i][j] - rot.getElement(i, j)) > EPSILON) {
                    return false;    
                }    
            }    
        }       
        
        return true;    
    }    
    

    /**
     * Returns the translation matrix.
     * 
     * @return double[] translation matrix with length 16.
     */
    public double[] getTranslateMatrix() {
        return createTranslationMatrix(tran[U_TRANSX], tran[U_TRANSY], tran[U_TRANSZ]);
    }
    
    /**
     * Returns the scale matrix.
     * 
     * @return double[] scale matrix with length 16.
     */
    public double[] getScaleMatrix() {
        return createScaleMatrix(tran[U_SCALEX], tran[U_SCALEY], tran[U_SCALEZ]);
    }
    
    /**
     * Returns the shear matrix.
     * 
     * @return double[] shear matrix with length 16.
     */
    public double[] getShearMatrix() {
        return createShearMatrix(tran[U_SHEARXY], tran[U_SHEARXZ], tran[U_SHEARXZ]); 
    }
    
    /**
     * Returns the perspective matrix.
     * 
     * @return double[] perspective matrix with length 16.
     */
    public double[] getPerspectiveMatrix() { 
        return createPerspectiveMatrix(tran[U_PERSPX], tran[U_PERSPY], tran[U_PERSPZ], tran[U_PERSPW]);
    }
    
    /**
     * Returns the rotation matrix R.
     * 
     * <pre>
     *       / 1               0               0        \
     * Rx =  | 0               Cos(rx)         -Sin(rx) |
     *       \ 0               Sin(rx)         Cos(rx)  /
     * 
     *       / Cos(ry)         0               Sin(ry)  \
     * Ry =  | 0               1               0        |
     *       \ -Sin(ry)        0               Cos(ry)  /
     * 
     *       / Cos(rz)         -Sin(rz)        0        \
     * Rz =  | Sin(rz)         Cos(rz)         0        |
     *       \ 0               0               1        /
     * 
     * R  = Rx.Ry.Rz
     * </pre>
     * 
     * @return double[] rotation matrix with length 16.
     */
    public double[] getRotationMatrix() {
        //Creating the rotation matrix
        double rx = tran[U_ROTATEX];
        double ry = tran[U_ROTATEY];
        double rz = tran[U_ROTATEZ];
        double[] m = new double[16];
        Arrays.fill(m, 0); 
        m[0] = Math.cos(ry)*Math.cos(rz);
        m[1] = -Math.cos(ry)*Math.sin(rz);
        m[2] = Math.sin(ry);
        m[4] = Math.cos(rz)*Math.sin(rx)*Math.sin(ry) +
               Math.cos(rx)*Math.sin(rz);
        m[5] = -Math.sin(rx)*Math.sin(ry)*Math.sin(rz) +
               Math.cos(rx)*Math.cos(rz);
        m[6] = -Math.cos(ry)*Math.sin(rx);
        m[8] = -Math.cos(rx)*Math.cos(rz)*Math.sin(ry) +
               Math.sin(rx)*Math.sin(rz);
        m[9] = Math.cos(rx)*Math.sin(ry)*Math.sin(rz) +
               Math.cos(rz)*Math.sin(rx);
        m[10] = Math.cos(rx)*Math.cos(ry);
        m[15] = 1.0;
        
        return m;
    }
    
    /**
     * Returns the three rotation angles rx, ry and rz.
     * 
     * <pre>
     *       / 1               0               0        \
     * Rx =  | 0               Cos(rx)         -Sin(rx) |
     *       \ 0               Sin(rx)         Cos(rx)  /
     * 
     *       / Cos(ry)         0               Sin(ry)  \
     * Ry =  | 0               1               0        |
     *       \ -Sin(ry)        0               Cos(ry)  /
     * 
     *       / Cos(rz)         -Sin(rz)        0        \
     * Rz =  | Sin(rz)         Cos(rz)         0        |
     *       \ 0               0               1        /
     * 
     * R  = Rx.Ry.Rz
     * </pre>
     * 
     * @return double[] the three rotation angles in the order {rx, ry, rz}.
     */
    public double[] getRotationParameter() {
        return new double[]{tran[U_ROTATEX], tran[U_ROTATEY], tran[U_ROTATEZ]};
    }
    
    /**
     * Returns the four parameter for the perspective transformation.
     * 
     * <pre>
     *         / 1      0     0    0 \
     *     P = | 0      1     0    0 |
     *         | 0      0     1    0 |
     *         \ x      y     z    w /
     * </pre>
     * 
     * @return double[] the four parameter of the perspecitve transformation
     *                {x, y, z, w}.
     */
    public double[] getPerspectiveParameter() {
        return new double[]{tran[U_PERSPX], tran[U_PERSPY], tran[U_PERSPZ], tran[U_PERSPW]};
    }
    
    /**
     * Returns the three parameter for the scale transformation.
     * 
     * <pre>
     *       / sx    0    0    0 \
     *   S = | 0     sy   0    0 |
     *       | 0     0    sz   0 |
     *       \ 0     0    0    1 /
     *     
     * 
     * </pre>
     * 
     * @return double[] the three parameter of the scale transformation {sx, sy, sz}.
     * 
     */
    public double[] getScaleParameter() {
        return new double[]{tran[U_SCALEX], tran[U_SCALEY], tran[U_SCALEZ]};
    }
    
    /**
     * Returns the three parameter of the shear transformation.
     * 
     *  <pre>
     *         / 1   shxy   shxz  \
     *   Sh =  | 0   1      shyz  |
     *         \ 0   0      1     /
     * 
     * 
     * </pre>
     * 
     * @return double[] the three parameter of the shear parameter {shxy, shxz, shyz}.
     */
    public double[] getShearParameter() {
        return new double[]{tran[U_SHEARXY], tran[U_SHEARXZ], tran[U_SHEARYZ]};
    }
    
    /**
     * Returns the three parameter of the translation transformation
     * 
     * <pre>
     *          / 1    0    0    dx \
     *          | 0    1    0    dy |
     *     T =  | 0    0    1    dz |
     *          \ 0    0    0    1  /
     * </pre>
     * 
     * @return double[] the three translation parameter {dx, dy, dz}.
     */
    public double[] getTranlationParameter() {
        return new double[]{tran[U_TRANSX], tran[U_TRANSY], tran[U_TRANSZ]};
    }
    
    public static double[] createTranslationMatrix(double dx, double dy, double dz) {
        double[] m = new double[16];
        Arrays.fill(m, 0.0);
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        m[15] = 1;
        m[3] = dx;
        m[7] = dy;
        m[11] = dz;
        
        return m;    }
    
    public static double[] createShearMatrix(double shxy, double shxz, double shyz) {
        double[] m = new double[16];
        Arrays.fill(m, 0.0);
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        m[15] = 1;
        m[1] = shxy;
        m[2] = shxz;
        m[6] = shyz;
        
        return m;
    }
    
    public static double[] createScaleMatrix(double sx, double sy, double sz) {
        double[] m = new double[16];
        Arrays.fill(m, 0.0);
        m[0] = sx;
        m[5] = sy;
        m[10] = sz;
        m[15] = 1;
        
        return m;
    }
    
    public static double[] createRotationMatrix(double rx, double ry, double rz) {
        double[] m = new double[16];
        Arrays.fill(m, 0); 
        m[0] = Math.cos(ry)*Math.cos(rz);
        m[1] = -Math.cos(ry)*Math.sin(rz);
        m[2] = Math.sin(ry);
        m[4] = Math.cos(rz)*Math.sin(rx)*Math.sin(ry) +
               Math.cos(rx)*Math.sin(rz);
        m[5] = -Math.sin(rx)*Math.sin(ry)*Math.sin(rz) +
               Math.cos(rx)*Math.cos(rz);
        m[6] = -Math.cos(ry)*Math.sin(rx);
        m[8] = -Math.cos(rx)*Math.cos(rz)*Math.sin(ry) +
               Math.sin(rx)*Math.sin(rz);
        m[9] = Math.cos(rx)*Math.sin(ry)*Math.sin(rz) +
               Math.cos(rz)*Math.sin(rx);
        m[10] = Math.cos(rx)*Math.cos(ry);
        m[15] = 1.0;
        
        return m;
    }
    
    public static double[] createPerspectiveMatrix(double px, double py, double pz, double pw) {
        double[] m = new double[16];
        Arrays.fill(m, 0.0);
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        m[12] = px;
        m[13] = py;
        m[14] = pz;
        m[15] = pw;
        
        return m;
    }
    
    public String toString() {
        String result = "U_SCALEX : (" + tran[U_SCALEX ] + ")\n" +
                        "U_SCALEY : (" + tran[U_SCALEY ] + ")\n" +
                        "U_SCALEZ : (" + tran[U_SCALEZ ] + ")\n" +
                        "U_SHEARXY: (" + tran[U_SHEARXY] + ")\n" +
                        "U_SHEARXZ: (" + tran[U_SHEARXZ] + ")\n" +
                        "U_SHEARYZ: (" + tran[U_SHEARYZ] + ")\n" +
                        "U_ROTATEX: (" + tran[U_ROTATEX] + ")\n" +
                        "U_ROTATEY: (" + tran[U_ROTATEY] + ")\n" +
                        "U_ROTATEZ: (" + tran[U_ROTATEZ] + ")\n" +
                        "U_TRANSX : (" + tran[U_TRANSX ] + ")\n" +
                        "U_TRANSY : (" + tran[U_TRANSY ] + ")\n" +
                        "U_TRANSZ : (" + tran[U_TRANSZ ] + ")\n" +
                        "U_PERSPX : (" + tran[U_PERSPX ] + ")\n" +
                        "U_PERSPY : (" + tran[U_PERSPY ] + ")\n" +
                        "U_PERSPZ : (" + tran[U_PERSPZ ] + ")\n" +
                        "U_PERSPW : (" + tran[U_PERSPW ] + ")\n";

        return result;        
    }

}









