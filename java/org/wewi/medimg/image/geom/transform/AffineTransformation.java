/*
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom.transform;

import java.util.Arrays;

import org.wewi.medimg.image.Image;

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
public class AffineTransformation implements Transformation, Interpolateable {
    private double[] matrix;
    private double[] inverseMatrix;
    
    private double[] tran;
    private AffineTransformation(double[] matrix, double[] inverseMatrix) {
        this.matrix = new double[12];
        this.inverseMatrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        System.arraycopy(inverseMatrix, 0, this.inverseMatrix, 0, 12);
        
        calculateScaleShearRotTransPersp();
    }

    public AffineTransformation(AffineTransformation transform) {
        matrix = new double[12];
        inverseMatrix = new double[12];
        System.arraycopy(transform.matrix, 0, matrix, 0, 12);
        System.arraycopy(transform.inverseMatrix, 0, inverseMatrix, 0, 12);
        
        calculateScaleShearRotTransPersp();
    }

    public AffineTransformation(double[] matrix) {
        this.matrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        inverseMatrix = invert(this.matrix);

        calculateScaleShearRotTransPersp();
    }
      

    public double[] getMatrix() {
        double[] m = new double[12];
        System.arraycopy(matrix, 0, m, 0, 9);
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
    
    private void calculateScaleShearRotTransPersp() {
        tran = new double[16];
        Arrays.fill(tran, 0);
        
        DoubleMatrix2D locmat = DoubleFactory2D.dense.make(4, 4);
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
            System.out.println("Singulär A");
            return; //Singuläre Matrix kann nicht behandelt werden.
        }
        double norm = locmat.getQuick(3, 3);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                locmat.setQuick(i, j, locmat.getQuick(i ,j)/norm);
            }
        }
                 
        //pmat wird zum Lösen des perspektivischen Anteils verwendet.
        //Es wird hier automatisch die ober 3x3 Komponente
        //auf Singularität getestet.
        DoubleMatrix2D pmat = locmat.copy();
        pmat.viewRow(3).assign(0);
        pmat.setQuick(3, 3, 1);
    
        
        if (Algebra.DEFAULT.det(pmat) == 0.0) {
            System.out.println("Singulär B");
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
            tran[U_TRANSX + i] = locmat.getQuick(i, 3);
            locmat.setQuick(i, 3, 0);
        }
    
        //Finden der Skalierung und Scherung
        DoubleMatrix2D rot = DoubleFactory2D.dense.make(3, 3);
        for (int i = 0; i < 3; i++) {
            rot.setQuick(0, i, locmat.getQuick(0, i));
            rot.setQuick(1, i, locmat.getQuick(1, i));
            rot.setQuick(2, i, locmat.getQuick(2, i));
        }
    
        //Berechnen der X-Skalierung und normalisieren der ersten Spalte
        tran[U_SCALEX] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(0)));
        rot.viewColumn(0).assign(Functions.mult(1/tran[U_SCALEX]));

    
        //Berechnen der XY-Scherung und orthogonalisieruen der ersten 
        //und zweiten Spalte der rot-Matrix.
        tran[U_SHEARXY] = rot.viewColumn(0).zDotProduct(rot.viewColumn(1));
        rot.viewColumn(1).assign(rot.viewColumn(0), new LinearCombination(1, -tran[U_SHEARXY]));
        
    
        //Berechnen der Y-Skalierung und normalisieren der zweiten Spalte
        tran[U_SCALEY] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(1)));
        rot.viewColumn(1).assign(Functions.mult(1/tran[U_SCALEY]));
        tran[U_SHEARXY] /= tran[U_SCALEY];
    
        //Berechnen der XZ und YZ-Scherungen und orthogonalisieren der dritten Spalte
        tran[U_SHEARXZ] = rot.viewColumn(0).zDotProduct(rot.viewColumn(2));
        rot.viewColumn(2).assign(rot.viewColumn(0), new LinearCombination(1, -tran[U_SHEARXZ])); 
        tran[U_SHEARYZ] = rot.viewColumn(1).zDotProduct(rot.viewColumn(2));
        rot.viewColumn(2).assign(rot.viewColumn(1), new LinearCombination(1, -tran[U_SHEARYZ]));               
    
        //Berechnen der Z-Skalierung und normalisieren der dritten Spalte.
        tran[U_SCALEZ] = Math.sqrt(Algebra.DEFAULT.norm2(rot.viewColumn(2)));
        rot.viewColumn(2).assign(Functions.mult(1/tran[U_SCALEZ]));
        tran[U_SHEARXZ] /= tran[U_SCALEZ];
        tran[U_SHEARYZ] /= tran[U_SCALEZ];
     
        /* At this point, the matrix (in rows[]) is orthonormal.
         * Check for a coordinate system flip.  If the determinant
         * is -1, then negate the matrix and the scaling factors.
         */
        DoubleMatrix2D pdum3 = Algebra.DEFAULT.multOuter(rot.viewColumn(1), rot.viewColumn(2), null);
        double temp = rot.viewColumn(0).zDotProduct(pdum3.viewColumn(0));
        if (temp < 0) {
            for (int i = 0; i < 3; i++ ) {
                tran[U_SCALEX+i] *= -1;
                rot.viewColumn(i).assign(Functions.mult(-1));
            }
        }
     
        // Ermitteln des Rotationsanteils
        tran[U_ROTATEY] = Math.asin(-rot.getQuick(2, 0));
        if (Math.cos(tran[U_ROTATEY]) != 0) {
            tran[U_ROTATEX] = Math.atan2(rot.getQuick(2, 1), rot.getQuick(2, 2));
            tran[U_ROTATEZ] = Math.atan2(rot.getQuick(1, 0), rot.getQuick(0, 0));
        } else {
            tran[U_ROTATEX] = Math.atan2(-rot.getQuick(0, 2), rot.getQuick(1, 1));
            tran[U_ROTATEZ] = 0;
        }
       
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
    ////////////////////////////////////////////////////////////////////////////
    

    public Transformation scale(double alpha) {
        matrix[0] *= alpha;
        matrix[5] *= alpha;
        matrix[10] *= alpha;
        inverseMatrix = invert(matrix);

        return this;
    }

    public Transformation concatenate(Transformation transform) {
        //transform*this
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

        B.zMult(A, A, 1, 0, false, false);

        pos = 0;
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[pos] = A.getQuick(i, j);
                pos++;
            }
        }

        inverseMatrix = invert(matrix);

        return this;
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
        Algebra algebra = new Algebra();
        DoubleMatrix2D inv = algebra.inverse(m);
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
    
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        transform(source, target);
        return target;
    }     

    public void transform(Image source, Image target) {
        target.resetColor(0);
        
        int tminX = target.getMinX();
        int tminY = target.getMinY();
        int tminZ = target.getMinZ();
        int tmaxX = target.getMaxX();
        int tmaxY = target.getMaxY();
        int tmaxZ = target.getMaxZ();

        int sminX = source.getMinX();
        int sminY = source.getMinY();
        int sminZ = source.getMinZ();
        int smaxX = source.getMaxX();
        int smaxY = source.getMaxY();
        int smaxZ = source.getMaxZ();

        int[] p = new int[3];
        int[] q = new int[3];
        int x, y, z;
        for (int i = tminX; i <= tmaxX; i++) {
            for (int j = tminY; j <= tmaxY; j++) {
                for (int k = tminZ; k <= tmaxZ; k++) {
                    p[0] = i; p[1] = j; p[2] = k;
                    transformBackward(p, q);
                    x = (int)q[0];
                    y = (int)q[1];
                    z = (int)q[2];
                    if (x <= smaxX && x >= sminX &&
                        y <= smaxY && y >= sminY &&
                        z <= smaxZ && z >= sminZ) {
                            target.setColor(i, j, k, source.getColor(x, y, z));
                    }
                }
            }
        }
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
    
    /**
     * @see org.wewi.medimg.image.geom.transform.Interpolateable#interpolate(Transformation, double)
     */
    public Transformation interpolate(Transformation trans2, double w) {
        return null;
    }    

    private void transformBackward(double[] source, double[] target) {
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

    private void transformBackward(float[] source, float[] target) {
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

    private void transformBackward(int[] source, int[] target) {
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
        String string = Double.toString(number);
        string += "000000000000000000000000000";
        if (string.length() >= length) {
            string = string.substring(0, length-1);
        }
        return string;
    }

    public String toString() {
        final int length = 8;
        StringBuffer buffer = new StringBuffer();
        buffer.append("AffineTransformation: 4x4\n");
        buffer.append("[\n");
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            buffer.append(" [");
            for (int j = 0; j < 4; j++) {
                buffer.append("(").append(format(matrix[pos++], length)).append(")");
            }
            buffer.append("]\n");
        }
        buffer.append(" [(").append(format(0, length)).append(")");
        buffer.append("(").append(format(0, length)).append(")");
        buffer.append("(").append(format(0, length)).append(")");
        buffer.append("(").append(format(1, length)).append(")]\n");
        buffer.append("]\n");
        
        buffer.append("\n Translation:\n");
        buffer.append(tran[U_TRANSX]).append(",");
        buffer.append(tran[U_TRANSY]).append(",");
        buffer.append(tran[U_TRANSZ]).append("\n");
        
        buffer.append("\n Scalierung:\n");
        buffer.append(tran[U_SCALEX]).append(",");
        buffer.append(tran[U_SCALEY]).append(",");
        buffer.append(tran[U_SCALEZ]).append("\n"); 
        
             
        buffer.append("\n Rotation:\n");
        buffer.append(tran[U_ROTATEX]).append(",");
        buffer.append(tran[U_ROTATEY]).append(",");
        buffer.append(tran[U_ROTATEZ]).append("\n"); 
                     

        return buffer.toString();
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        double[] matrix = {-3.32917, 26.0387, 1.53029, 23., -7.27438, -11.9168, -0.700351, 
    1., 0., -44.598, 1.0806, 65., 0., 0., 0., 1.};
        AffineTransformation transform = new AffineTransformation(matrix);
        System.out.println(transform);                    
    
    }

}
