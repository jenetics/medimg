/*
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom.transform;

import java.text.NumberFormat;
import java.util.Arrays;

import org.wewi.medimg.image.Image;
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
public class AffineTransformation implements Transformation, 
                                                Interpolateable,
                                                Immutable {
    private double[] matrix;
    private double[] inverseMatrix;
    
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
        this.matrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        inverseMatrix = invert(this.matrix);

        unmatrix();
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
        DoubleMatrix1D pdum3 = cross(rot.viewColumn(1), rot.viewColumn(2));
        double temp = rot.viewColumn(0).zDotProduct(pdum3);
        if (temp < 0) {
            for (int i = 0; i < 3; i++ ) {
                tran[U_SCALEX+i] *= -1;
                rot.viewColumn(i).assign(Functions.mult(-1));
            }
        }             
     
        // Ermitteln des Rotationsanteils
        tran[U_ROTATEY] = Math.acos(rot.viewColumn(2).getQuick(2));
        
        tran[U_ROTATEX] = Math.asin(rot.viewColumn(0).getQuick(2)/Math.sin(tran[U_ROTATEY]));
        
        tran[U_ROTATEZ] = Math.acos(rot.viewColumn(2).getQuick(1)/Math.sin(tran[U_ROTATEY]));
        
        /*if (Math.cos(tran[U_ROTATEY]) != 0) {
            tran[U_ROTATEX] = Math.atan2(rot.viewColumn(1).getQuick(2), rot.viewColumn(2).getQuick(2));
            tran[U_ROTATEZ] = Math.atan2(rot.viewColumn(0).getQuick(1), rot.viewColumn(0).getQuick(0));
        } else {
            tran[U_ROTATEX] = Math.atan2(-rot.viewColumn(2).getQuick(0), rot.viewColumn(1).getQuick(1));
            tran[U_ROTATEZ] = 0;
        } */
         
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
        double[] m = new double[12];
        Arrays.fill(m, 0);
        m[3] = tran[U_TRANSX];
        m[7] = tran[U_TRANSY];
        m[11] = tran[U_TRANSZ];
        
        //Rotationsteil ist Einheitsmatrix
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;        
        
        return new AffineTransformation(m);    
    }
    
    public AffineTransformation getRotationTransformation() {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        
        double a = tran[U_ROTATEX];
        double b = tran[U_ROTATEY];
        double c = tran[U_ROTATEZ];
        m[0] = Math.cos(a)*Math.cos(c) - Math.cos(b)*Math.sin(a)*Math.sin(c);
        m[1] = Math.cos(c)*Math.sin(a) + Math.cos(a)*Math.cos(b)*Math.sin(c);
        m[2] = Math.sin(b)*Math.sin(c);
        m[4] = -(Math.cos(b)*Math.cos(c)*Math.sin(a)) - Math.cos(a)*Math.sin(c);
        m[5] = Math.cos(a)*Math.cos(b)*Math.cos(c) - Math.sin(a)*Math.sin(c);
        m[6] = Math.cos(c)*Math.sin(b);
        m[8] = Math.sin(a)*Math.sin(b);
        m[9] = -(Math.cos(a)*Math.sin(b));
        m[10] = Math.cos(b);
        
        return new AffineTransformation(m);    
    }
       
    
    public AffineTransformation getShearTransformation() {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        
        //Rotationsteil ist Einheitsmatrix
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;        
        
        return null;    
    }
    
    public AffineTransformation getScaleTransformation() {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        
        m[0] = tran[U_SCALEX];
        m[5] = tran[U_SCALEY];
        m[10] = tran[U_SCALEZ];
        
        return new AffineTransformation(m);    
    }

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

        A.zMult(B, B, 1, 0, false, false);

        pos = 0;
        double[] m = new double[12];
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m[pos] = B.getQuick(i, j);
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
    public Transformation interpolate(Transformation trans2, double w) throws IllegalArgumentException {
        if (!(trans2 instanceof AffineTransformation)) {
            throw new IllegalArgumentException("trans2 not an AffineTransformation: " + 
                                                getClass().getName() + ".interpolate()");   
        }
        
        AffineTransformation t2 = (AffineTransformation)trans2;
        
        double[] transInterpol = new double[16];
        for (int i = 0; i < 16; i++) {
            transInterpol[i] = tran[i]*(1-w) + t2.tran[i]*w;    
        }
        
        double[] m = new double[12];
        Arrays.fill(m, 0);
        m[0] = transInterpol[U_SCALEX];
        m[5] = transInterpol[U_SCALEY];
        m[10] = transInterpol[U_SCALEZ];
        AffineTransformation As = new AffineTransformation(m);  
        
        Arrays.fill(m, 0);
        double a = transInterpol[U_ROTATEX];
        double b = transInterpol[U_ROTATEY];
        double c = transInterpol[U_ROTATEZ];
        m[0] = Math.cos(a)*Math.cos(c) - Math.cos(b)*Math.sin(a)*Math.sin(c);
        m[1] = Math.cos(c)*Math.sin(a) + Math.cos(a)*Math.cos(b)*Math.sin(c);
        m[2] = Math.sin(b)*Math.sin(c);
        m[4] = -(Math.cos(b)*Math.cos(c)*Math.sin(a)) - Math.cos(a)*Math.sin(c);
        m[5] = Math.cos(a)*Math.cos(b)*Math.cos(c) - Math.sin(a)*Math.sin(c);
        m[6] = Math.cos(c)*Math.sin(b);
        m[8] = Math.sin(a)*Math.sin(b);
        m[9] = -(Math.cos(a)*Math.sin(b));
        m[10] = Math.cos(b);
        AffineTransformation Ar = new AffineTransformation(m);  
       
        Arrays.fill(m, 0);
        m[3] = transInterpol[U_TRANSX];
        m[7] = transInterpol[U_TRANSY];
        m[11] = transInterpol[U_TRANSZ];
        //Rotationsteil ist Einheitsmatrix
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;         
        AffineTransformation At = new AffineTransformation(m); 
        
                 
        return At.concatenate(Ar.concatenate(As));
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
    
    
    
    
    
    
    public static void main(String[] args) {
        double[][] m0 = {{7.54204, 17.6748, 0., 23.}, {1.39266, -26.0827, 5.11764, 
    34.}, {2.27556, -42.618, -3.13205, 65.}, {0., 0., 0., 1.}};
    
    
        double[] matrix = new double[12];
        int pos = 0;
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[pos] = m0[i][j];
                ++pos;
            }
        }
        
        

    
        AffineTransformation transform = new AffineTransformation(matrix);
        System.out.println(transform);  
        
        System.out.println("Rotation:");
        System.out.println(transform.getRotationTransformation());
        
        System.out.println("Translation:");
        System.out.println(transform.getTranslateTransformation());
        
        System.out.println("Skalierung:");
        System.out.println(transform.getScaleTransformation()); 
        
        System.out.println("Scherung:");
        System.out.println(transform.getShearTransformation());                       
        
    }

}
















