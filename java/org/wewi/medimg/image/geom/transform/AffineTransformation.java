/**
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom.transform;

import java.text.NumberFormat;
import java.util.Arrays;

import javax.vecmath.Matrix4d;

import org.wewi.medimg.util.Immutable;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;



/**
 *
 * @author Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public class AffineTransformation extends ImageTransformation 
                                   implements InterpolateableTransformation, Immutable {
    private double[] matrix;
    private double[] inverseMatrix;
    
    protected Matrix4d mat;
    protected Matrix4d invMat;
    
    private AffineTransformation(double[] matrix, double[] inverseMatrix) {
        this.matrix = new double[12];
        this.inverseMatrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        System.arraycopy(inverseMatrix, 0, this.inverseMatrix, 0, 12);
        
        unmatrix();
    }

    public AffineTransformation(AffineTransformation transform) {
        matrix = new double[12];
        inverseMatrix = new double[12];
        System.arraycopy(transform.matrix, 0, matrix, 0, 12);
        System.arraycopy(transform.inverseMatrix, 0, inverseMatrix, 0, 12);
        
        unmatrix();
    }

    public AffineTransformation(double[] matrix) {
        init(matrix);
    }
      
    public AffineTransformation(double[][] matrix) {
        double[] m = new double[12];
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m[count] = matrix[i][j];
                ++count;
            }
        }
        init(m);
    }
    
    private void init(double[] matrix) {
        this.matrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        inverseMatrix = invert(this.matrix);

        unmatrix();        
    }
    
    public double[] getMatrixArray() {
        double[] m = new double[12];
        System.arraycopy(matrix, 0, m, 0, 12);
        return m;
    }
    
    public double[][] getMatrix() {
        double[][] m = new double[4][4];
        
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0;j < 4; j++) {
                m[i][j] = matrix[count];
                count++;   
            }
        }
        for (int i = 0; i < 3; i++) {
            m[3][i] = 0;
        }
        m[3][3] = 1;
        
        return m;
    }
    
    
    /**
     * Dieser Code wurde aus Graphics Gems entnommen und 
     * für Java umgeschrieben. Originalcode <code>unmatrix.c</code>, Methode
     * <code>unmatrix</code>. 
     * 
     * <url>http://www1.acm.org/pubs/tog/GraphicsGems/gemsii/unmatrix.c</url>
     * 
     * Das Ergebnis wird in der selben Reihenfolge
     * in den Vektor trans geschrieben, wie im Originalcode.<p/><hl>
     * 
     * unmatrix.c - given a 4x4 matrix, decompose it into standard operations.
     * 
     * unmatrix - Decompose a non-degenerate 4x4 transformation matrix into
     *  the sequence of transformations that produced it.
     * [Sx][Sy][Sz][Shearx/y][Sx/z][Sz/y][Rx][Ry][Rz][Tx][Ty][Tz][P(x,y,z,w)]
     *
     * The coefficient of each transformation is returned in the corresponding
     * element of the vector tran.  
     *
     * @author:  (C-Code) Spencer W. Thomas; University of Michigan
     *
     *
     */
    private final static int U_SCALEX = 0;
    private final static int U_SCALEY = 1;
    private final static int U_SCALEZ = 2;
    private final static int U_SHEARXY = 3;
    private final static int U_SHEARXZ = 4;
    private final static int U_SHEARYZ = 5;
    private final static int U_ROTATEX = 6;
    private final static int U_ROTATEY = 7;
    private final static int U_ROTATEZ = 8;
    private final static int U_TRANSX = 9;
    private final static int U_TRANSY = 10;
    private final static int U_TRANSZ = 11;
    private final static int U_PERSPX = 12;
    private final static int U_PERSPY = 13;
    private final static int U_PERSPZ = 14;
    private final static int U_PERSPW = 15;
    
    private double[] tran;
    
    private void unmatrix() {
        tran = new double[16];
        Arrays.fill(tran, 0);
        
        DoubleMatrix2D locmat = DoubleFactory2D.dense.make(4, 4);
        locmat.assign(0);
        int pos = 0;
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                locmat.setQuick(i, j, matrix[pos]);
                ++pos;
            }
        }
        locmat.setQuick(3, 3, 1);
        
                
        //Normalisieren der Matrix 
        if (locmat.getQuick(3, 3) == 0) {
            System.err.println("Singulär A");
            return; //Singuläre Matrix kann nicht behandelt werden.
        }
        locmat.assign(Functions.div(locmat.getQuick(3, 3)));
          
                 
        //pmat wird zum Lösen des perspektivischen Anteils verwendet.
        //Es wird hier automatisch die ober 3x3 Komponente
        //auf Singularität getestet.
        DoubleMatrix2D pmat = locmat.copy();        
              
        pmat.viewRow(3).assign(0);
        pmat.setQuick(3, 3, 1);
          
        if (Algebra.DEFAULT.det(pmat) == 0.0) {
            System.err.println("Singulär B");
            return; //Singuläre Matrix kann nicht behandelt werden.
        }
    
        //Isolieren des perspektivischen Anteils
        DoubleMatrix1D prsh = DoubleFactory1D.dense.make(4);
        if (locmat.getQuick(3, 0) != 0 || 
            locmat.getQuick(3, 1) != 0 ||
            locmat.getQuick(3, 2) != 0 ) {
                
            //prhs ist die rechte Seite der Gleichung
            for (int i = 0; i < 4; i++) {
                prsh.setQuick(i, locmat.getQuick(3, i));
            }
            /*Lösen der Gleichung durch Invertieren von pmat 
             * und multiplizieren mit prsh
             */
            DoubleMatrix2D invpmat = Algebra.DEFAULT.inverse(pmat);
            DoubleMatrix1D psol = DoubleFactory1D.dense.make(4);
            invpmat.zMult(prsh, psol, 1, 0, true);
     
            //Speichern der Lösung
            tran[U_PERSPX] = psol.getQuick(0);
            tran[U_PERSPY] = psol.getQuick(1);
            tran[U_PERSPZ] = psol.getQuick(2);
            tran[U_PERSPW] = psol.getQuick(3);

            //Entfernen des perspektivischen Anteils
            locmat.viewRow(3).assign(0);
            locmat.setQuick(3, 3, 1);
        } else {     //Kein perspektivischer Anteil
            tran[U_PERSPX] = tran[U_PERSPY] = tran[U_PERSPZ] = tran[U_PERSPW] = 0;
        }
    
        //Finden der Translation und entfernen der Translation
        for (int i = 0; i < 3; i++) {
            tran[U_TRANSX + i] = locmat.viewColumn(3).getQuick(i);
        }
        locmat.viewColumn(3).assign(0);
    
        //Finden der Skalierung und Scherung
        DoubleMatrix2D rot = locmat.viewPart(0, 0, 3, 3).copy();

        //Berechnen der X-Skalierung und normalisieren der ersten Spalte
        tran[U_SCALEX] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(0)));
        rot.viewColumn(0).assign(Functions.div(tran[U_SCALEX]));
             
    
        //Berechnen der XY-Scherung und orthogonalisieruen der ersten 
        //und zweiten Spalte der rot-Matrix.
        tran[U_SHEARXY] = rot.viewColumn(0).zDotProduct(rot.viewColumn(1));
        rot.viewColumn(1).assign(rot.viewColumn(0), new LinearCombination(1.0, -tran[U_SHEARXY]));
           
    
        //Berechnen der Y-Skalierung und normalisieren der zweiten Spalte
        tran[U_SCALEY] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(1)));
        rot.viewColumn(1).assign(Functions.div(tran[U_SCALEY]));
        tran[U_SHEARXY] /= tran[U_SCALEY];
               
    
        //Berechnen der XZ und YZ-Scherungen und orthogonalisieren der dritten Spalte
        tran[U_SHEARXZ] = rot.viewColumn(0).zDotProduct(rot.viewColumn(2));
        rot.viewColumn(2).assign(rot.viewColumn(0), new LinearCombination(1, -tran[U_SHEARXZ])); 
        tran[U_SHEARYZ] = rot.viewColumn(1).zDotProduct(rot.viewColumn(2));
        rot.viewColumn(2).assign(rot.viewColumn(1), new LinearCombination(1, -tran[U_SHEARYZ]));               
   
    
        //Berechnen der Z-Skalierung und normalisieren der dritten Spalte.
        tran[U_SCALEZ] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(2)));
        rot.viewColumn(2).assign(Functions.div(tran[U_SCALEZ]));
        tran[U_SHEARXZ] /= tran[U_SCALEZ];
        tran[U_SHEARYZ] /= tran[U_SCALEZ];
               
     
        /* At this point, the matrix (in rows[]) is orthonormal.
         * Check for a coordinate system flip.  If the determinant
         * is -1, then negate the matrix and the scaling factors.
         */

        Algebra algebra = new Algebra();
        double det = algebra.det(rot);
        if (det < 0) {
        	//System.out.println("Bin drinnen" + temp + " " + temp2);
            for (int i = 0; i < 3; i++ ) {
                tran[U_SCALEX+i] *= -1;
                rot.viewColumn(i).assign(Functions.mult(-1));
            }
        } 
        
        
        tran[U_ROTATEY] = Math.asin(rot.viewColumn(2).getQuick(0));
        if (Math.cos(tran[U_ROTATEY]) != 0) {
            tran[U_ROTATEX] = Math.atan(-rot.viewColumn(2).getQuick(1)/rot.viewColumn(2).getQuick(2));
            tran[U_ROTATEZ] = Math.atan(-rot.viewColumn(1).getQuick(0)/rot.viewColumn(0).getQuick(0));
        } else {
            tran[U_ROTATEX] = 0;
            tran[U_ROTATEZ] = Math.asin(rot.viewColumn(0).getQuick(1));                
        }
        
                          
        double[] a = new double[2];
        double[] b = new double[2];
        double[] c = new double[2];
        
        a[0] = tran[U_ROTATEX];
        a[1] = Math.PI + a[0];
        b[0] = tran[U_ROTATEY];
        b[1] = Math.PI - b[0];
        c[0] = tran[U_ROTATEZ];
        c[1] = Math.PI + c[0];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if (testAngles(a[i], b[j], c[k], rot, 0.1)) {
                        tran[U_ROTATEX] = a[i]; 
                        tran[U_ROTATEY] = b[j];
                        tran[U_ROTATEZ] = c[k]; 
                        return;  
                    }    
                }    
            }    
        }
 
/*        
        System.out.print( "U_SCALEX : (" + tran[U_SCALEX ] + ")\n");
        System.out.print( "U_SCALEY : (" + tran[U_SCALEY ] + ")\n");
        System.out.print( "U_SCALEZ : (" + tran[U_SCALEZ ] + ")\n");
        System.out.print( "U_SHEARXY: (" + tran[U_SHEARXY] + ")\n");
        System.out.print( "U_SHEARXZ: (" + tran[U_SHEARXZ] + ")\n");
        System.out.print( "U_SHEARYZ: (" + tran[U_SHEARYZ] + ")\n");
        System.out.print( "U_ROTATEX: (" + tran[U_ROTATEX] + ")\n");
        System.out.print( "U_ROTATEY: (" + tran[U_ROTATEY] + ")\n");
        System.out.print( "U_ROTATEZ: (" + tran[U_ROTATEZ] + ")\n");
        System.out.print( "U_TRANSX : (" + tran[U_TRANSX ] + ")\n");
        System.out.print( "U_TRANSY : (" + tran[U_TRANSY ] + ")\n");
        System.out.print( "U_TRANSZ : (" + tran[U_TRANSZ ] + ")\n");
        System.out.print( "U_PERSPX : (" + tran[U_PERSPX ] + ")\n");
        System.out.print( "U_PERSPY : (" + tran[U_PERSPY ] + ")\n");
        System.out.print( "U_PERSPZ : (" + tran[U_PERSPZ ] + ")\n");
        System.out.print( "U_PERSPW : (" + tran[U_PERSPW ] + ")\n");
*/         
    }
    
    private DoubleMatrix1D cross(DoubleMatrix1D a, DoubleMatrix1D b) {
        DoubleMatrix1D c = DoubleFactory1D.dense.make(3);
        
        c.setQuick(0, a.getQuick(1)*b.getQuick(2) - a.getQuick(2)*b.getQuick(1));
        c.setQuick(1, a.getQuick(2)*b.getQuick(0) - a.getQuick(0)*b.getQuick(2));
        c.setQuick(2, a.getQuick(0)*b.getQuick(1) - a.getQuick(1)*b.getQuick(0));

        return c;    
    }
    
    private class LinearCombination implements DoubleDoubleFunction {
        private double ascl, bscl;
        
        public LinearCombination(double ascl, double bscl) {
            this.ascl = ascl; this.bscl = bscl;    
        }
        
		public double apply(double a, double b) {
			return ascl*a + bscl*b;
		}

    }
    
    private boolean testAngles(double a, double b, double c, DoubleMatrix2D rot, double EPSILON) {
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
                if (Math.abs(m[i][j] - rot.getQuick(i, j)) > EPSILON) {
                    return false;    
                }    
            }    
        }       
        
        return true;    
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    public AffineTransformation getPerspectiveTransformation() {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        m[8] = tran[U_PERSPX];
        m[9] = tran[U_PERSPY];
        m[10] = tran[U_PERSPZ];
        m[11] = tran[U_PERSPW];
        
        //Rotationsteil ist Einheitsmatrix
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        
        return new AffineTransformation(m);    
    }
    
    
    public AffineTransformation getTranslateTransformation() {
        return getTranslateInstance(new double[]{tran[U_TRANSX], tran[U_TRANSY], tran[U_TRANSZ]});    
    }
    
    public AffineTransformation getRotationTransformation() {
        return getRotateInstance(new double[]{tran[U_ROTATEX], tran[U_ROTATEY], tran[U_ROTATEZ]});    
    }
       
    public AffineTransformation getShearTransformation() {            
        return getShearInstance(new double[]{tran[U_SHEARXY], tran[U_SHEARXZ], tran[U_SHEARYZ]});    
    }
    
    public AffineTransformation getScaleTransformation() {
        return getScaleInstance(new double[]{tran[U_SCALEX], tran[U_SCALEY], tran[U_SCALEY]});    
    }

    public Transformation scale(double alpha) {
        double[] m = getMatrixArray();
        
        m[0] *= alpha;
        m[5] *= alpha;
        m[10] *= alpha;

        return new AffineTransformation(m);
    }

    public Transformation concatenate(Transformation transform) throws IllegalArgumentException {
        if (!(transform instanceof AffineTransformation)) {
            throw new IllegalArgumentException("Argument transform is not an " + 
                                                 "instance of " + getClass().getName());
        }
        
        
        DoubleMatrix2D A = new DenseDoubleMatrix2D(4, 4);
        DoubleMatrix2D B = new DenseDoubleMatrix2D(4, 4);
        A.assign(0);
        B.assign(0);
        A.setQuick(3, 3, 1);
        B.setQuick(3, 3, 1);
        int pos = 0;

        AffineTransformation t = (AffineTransformation)transform;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                A.setQuick(i, j, matrix[pos]);
                B.setQuick(i, j, t.matrix[pos]);
                pos++;
            }
        }

        DoubleMatrix2D C = A.zMult(B, null, 1, 0, false, false);

        pos = 0;
        double[] m = new double[12];
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m[pos] = C.getQuick(i, j);
                pos++;
            }
        }

        return new AffineTransformation(m);        
    }

    private double[] invert(double[] matrix) {
        double[] inverse = new double[12];
        DoubleMatrix2D m = new DenseDoubleMatrix2D(4, 4);
        m.assign(0);
        m.setQuick(3, 3, 1);
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m.setQuick(i, j, matrix[pos++]);
            }
        }
        DoubleMatrix2D inv;
        Algebra algebra = new Algebra();
	    inv = algebra.inverse(m);
        pos = 0;
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                inverse[pos++] = inv.getQuick(i, j);
            }
        }

        return inverse;
    }

    public Transformation createInverse() {
        return new AffineTransformation(inverseMatrix, matrix);
    }
    
    public static AffineTransformation getTranslateInstance(double[] transXYZ) {
        double[] m = new double[12];  
        Arrays.fill(m, 0);
        
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        
        m[3] = transXYZ[0];
        m[7] = transXYZ[1];
        m[11] = transXYZ[2];
        
        return new AffineTransformation(m);  
    }
    
    /**
     * Die gesamte Rotation <code>R</code> setzt sich aus der Rotation
     * setzt sich aus der Rotation um die X-, Y- und Z-Achse
     * zusammen.
     * 
     * <pre>
     *       / 1               0               0               \
     * Rx =  | 0               Cos(rotXYZ[0])  -Sin(rotXYZ[0]) |
     *       \ 0               Sin(rotXYZ[0])  Cos[rotXYZ[0])  /
     * 
     *       / Cos(rotXYZ[1])  0               Sin(rotXYZ[1])  \
     * Ry =  | 0               1               0               |
     *       \ -Sin(rotXYZ[1]) 0               Cos(rotXYZ[1])  /
     * 
     *       / Cos(rotXYZ[2])  -Sin(rotXYZ[2])  0              \
     * Rz =  | Sin(rotXYZ[2])  Cos(rotXYZ[2])   0              |
     *       \ 0               0                1              /
     * 
     * R  = Rx.Ry.Rz
     * </pre>
     * 
     * 
     * 
     * @param rotXYZ die Winkel um die Rotiert werden soll. <code>rotXYZ[0]</code>
     *                Rotation um die x-Achse, <code>rotXYZ[1]</code> Rotation um die
     *                y-Achse und <code>rotXYZ[2]</code> Rotation um die z-Achse.
     *                Dabei wird die Rotation um die z-Achse zuerst ausgeführt. Die
     *                Rotation um die x-Achse kommt zum Schluß. (Kardanwinkel.)
     * 
     */
    public static AffineTransformation getRotateInstance(double[] rotXYZ) {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        m[0] = Math.cos(rotXYZ[1])*Math.cos(rotXYZ[2]);
        m[1] = -Math.cos(rotXYZ[1])*Math.sin(rotXYZ[2]);
        m[2] = Math.sin(rotXYZ[1]);
        m[4] = Math.cos(rotXYZ[2])*Math.sin(rotXYZ[0])*Math.sin(rotXYZ[1]) +
               Math.cos(rotXYZ[0])*Math.sin(rotXYZ[2]);
        m[5] = -Math.sin(rotXYZ[0])*Math.sin(rotXYZ[1])*Math.sin(rotXYZ[2]) +
               Math.cos(rotXYZ[0])*Math.cos(rotXYZ[2]);
        m[6] = -Math.cos(rotXYZ[1])*Math.sin(rotXYZ[0]);
        m[8] = -Math.cos(rotXYZ[0])*Math.cos(rotXYZ[2])*Math.sin(rotXYZ[1]) +
               Math.sin(rotXYZ[0])*Math.sin(rotXYZ[2]);
        m[9] = Math.cos(rotXYZ[0])*Math.sin(rotXYZ[1])*Math.sin(rotXYZ[2]) +
               Math.cos(rotXYZ[2])*Math.sin(rotXYZ[0]);
        m[10] = Math.cos(rotXYZ[0])*Math.cos(rotXYZ[1]);
        
        return new AffineTransformation(m); 
    }
    
    public static AffineTransformation getScaleInstance(double[] scaleXYZ) {
        double[] m = new double[12];  
        Arrays.fill(m, 0);
        
        m[0] = scaleXYZ[0];
        m[5] = scaleXYZ[1];
        m[10] = scaleXYZ[2];
        
        return new AffineTransformation(m);      
    }
    
   
    /**
     * Die Scherungsmatrix <code>S</code> sieht folgendermaßen aus.
     * 
     * <pre>
     *         / 1   shear[0]   shear[1]  \
     *   S =   | 0   1          shear[2]  |
     *         \ 0   0          1         /
     * 
     * 
     * </pre>
     * 
     * @param shear[] mit Sxy = <code>shear[0]</code>, 
     *                     Sxz = <code>shear[1]</code> und 
     *                     Syz = <code>shear[2]</code>
     * 
     * @return AffineTransformation mit der entsprechenden Scherung.
     * 
     */
    public static AffineTransformation getShearInstance(double[] shear) {
        double[] m = new double[12];  
        Arrays.fill(m, 0);
        
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        
        m[1] = shear[0];
        m[2] = shear[1];
        m[6] = shear[2];
        
        return new AffineTransformation(m);    
    }
    
    public void transform(double[] source, double[] target) {
        double x = matrix[0] * source[0] +
                   matrix[1] * source[1] +
                   matrix[2] * source[2] +
                   matrix[3];

        double y = matrix[4] * source[0] +
                   matrix[5] * source[1] +
                   matrix[6] * source[2] +
                   matrix[7];

        double z = matrix[8] * source[0] +
                   matrix[9] * source[1] +
                   matrix[10] * source[2] +
                   matrix[11];

        target[0] = x;
        target[1] = y;
        target[2] = z;
    }

    public void transform(float[] source, float[] target) {
        float x = (float)matrix[0] * source[0] +
                  (float)matrix[1] * source[1] +
                  (float)matrix[2] * source[2] +
                  (float)matrix[3];

        float y = (float)matrix[4] * source[0] +
                  (float)matrix[5] * source[1] +
                  (float)matrix[6] * source[2] +
                  (float)matrix[7];

        float z = (float)matrix[8] * source[0] +
                  (float)matrix[9] * source[1] +
                  (float)matrix[10] * source[2] +
                  (float)matrix[11];

        target[0] = x;
        target[1] = y;
        target[2] = z;
    }

    public void transform(int[] source, int[] target) {
        float x = (float)matrix[0] * (float)source[0] +
                  (float)matrix[1] * (float)source[1] +
                  (float)matrix[2] * (float)source[2] +
                  (float)matrix[3];

        float y = (float)matrix[4] * (float)source[0] +
                  (float)matrix[5] * (float)source[1] +
                  (float)matrix[6] * (float)source[2] +
                  (float)matrix[7];

        float z = (float)matrix[8] * (float)source[0] +
                  (float)matrix[9] * (float)source[1] +
                  (float)matrix[10] * (float)source[2] +
                  (float)matrix[11];

        target[0] = (int)Math.round(x);
        target[1] = (int)Math.round(y);
        target[2] = (int)Math.round(z);
    }
    

    public InterpolateableTransformation interpolate(InterpolateableTransformation trans2, double w) throws IllegalArgumentException {
        if (!(trans2 instanceof AffineTransformation)) {
            throw new IllegalArgumentException("trans2 not an AffineTransformation: " + 
                                                getClass().getName() + ".interpolate()");   
        }
        
        AffineTransformation t2 = (AffineTransformation)trans2;
                
        double[] transInterpol = new double[16];
        for (int i = 0; i < 16; i++) {
            transInterpol[i] = tran[i]*(1-w) + t2.tran[i]*w;    
        }
        
        double a = transInterpol[U_SCALEX];
        double b = transInterpol[U_SCALEY];
        double c = transInterpol[U_SCALEZ];
        AffineTransformation As = getScaleInstance(new double[]{a, b, c});
        
        a = transInterpol[U_SHEARXY];
        b = transInterpol[U_SHEARXZ];
        c = transInterpol[U_SHEARYZ];
        AffineTransformation Ash = getShearInstance(new double[]{a, b, c});  
        
        a = transInterpol[U_ROTATEX];
        b = transInterpol[U_ROTATEY];
        c = transInterpol[U_ROTATEZ];
        AffineTransformation Ar = getRotateInstance(new double[]{a, b, c});  
       
        a = transInterpol[U_TRANSX];
        b = transInterpol[U_TRANSY];
        c = transInterpol[U_TRANSZ];       
        AffineTransformation At = getTranslateInstance(new double[]{a, b, c}); 
                
        return (AffineTransformation)At.concatenate(Ar.concatenate(Ash.concatenate(As)));
    }    

    public void transformBackward(double[] source, double[] target) {
        double x = inverseMatrix[0] * source[0] +
                   inverseMatrix[1] * source[1] +
                   inverseMatrix[2] * source[2] +
                   inverseMatrix[3];

        double y = inverseMatrix[4] * source[0] +
                   inverseMatrix[5] * source[1] +
                   inverseMatrix[6] * source[2] +
                   inverseMatrix[7];

        double z = inverseMatrix[8] * source[0] +
                   inverseMatrix[9] * source[1] +
                   inverseMatrix[10] * source[2] +
                   inverseMatrix[11];

        target[0] = x;
        target[1] = y;
        target[2] = z;
    }

    public void transformBackward(float[] source, float[] target) {
        float x = (float)inverseMatrix[0] * source[0] +
                  (float)inverseMatrix[1] * source[1] +
                  (float)inverseMatrix[2] * source[2] +
                  (float)inverseMatrix[3];

        float y = (float)inverseMatrix[4] * source[0] +
                  (float)inverseMatrix[5] * source[1] +
                  (float)inverseMatrix[6] * source[2] +
                  (float)inverseMatrix[7];

        float z = (float)inverseMatrix[8] * source[0] +
                  (float)inverseMatrix[9] * source[1] +
                  (float)inverseMatrix[10] * source[2] +
                  (float)inverseMatrix[11];

        target[0] = x;
        target[1] = y;
        target[2] = z;
    }

    public void transformBackward(int[] source, int[] target) {
        float x = (float)inverseMatrix[0] * (float)source[0] +
                  (float)inverseMatrix[1] * (float)source[1] +
                  (float)inverseMatrix[2] * (float)source[2] +
                  (float)inverseMatrix[3];

        float y = (float)inverseMatrix[4] * (float)source[0] +
                  (float)inverseMatrix[5] * (float)source[1] +
                  (float)inverseMatrix[6] * (float)source[2] +
                  (float)inverseMatrix[7];

        float z = (float)inverseMatrix[8] * (float)source[0] +
                  (float)inverseMatrix[9] * (float)source[1] +
                  (float)inverseMatrix[10] * (float)source[2] +
                  (float)inverseMatrix[11];

        target[0] = (int)Math.round(x);
        target[1] = (int)Math.round(y);
        target[2] = (int)Math.round(z);
    }


    private String format(double number, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(length);
        
        return nf.format(number);
    }

    public String toString() {
        final int length = 5;
        StringBuffer buffer = new StringBuffer();
        buffer.append("AffineTransformation: 4x4\n");
        buffer.append("[\n");
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            buffer.append(" [");
            for (int j = 0; j < 4; j++) {
                buffer.append("(").append(format(matrix[pos], length)).append(")");
                ++pos;
            }
            buffer.append("]\n");
        }
        buffer.append(" [(").append(format(0, length)).append(")");
        buffer.append("(").append(format(0, length)).append(")");
        buffer.append("(").append(format(0, length)).append(")");
        buffer.append("(").append(format(1, length)).append(")]\n");
        buffer.append("]\n");
                     
        return buffer.toString();
    }
    

}


