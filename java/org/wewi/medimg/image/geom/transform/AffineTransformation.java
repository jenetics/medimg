/**
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom.transform;

import java.util.Arrays;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import org.wewi.medimg.util.Immutable;


/**
 *
 * @author Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public class AffineTransformation extends ImageTransformation 
                                   implements InterpolateableTransformation, Immutable {
    
    protected Matrix4d matrix;
    protected Matrix4d inverseMatrix;
    
    protected UnMatrix unmatrix;
    
    private AffineTransformation(Matrix4d matrix, Matrix4d inverseMatrix) {
        this.matrix = (Matrix4d)matrix.clone();
        this.inverseMatrix = (Matrix4d)matrix.clone();
        
        unmatrix = new UnMatrix(matrix);
    }
    
    private AffineTransformation(Matrix4d matrix) {
        this.matrix = (Matrix4d)matrix.clone();
        inverseMatrix = (Matrix4d)matrix.clone();
        inverseMatrix.invert();
        
        unmatrix = new UnMatrix(matrix);
    }

    public AffineTransformation(AffineTransformation transform) {
        this(transform.matrix, transform.inverseMatrix);
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
    
    private void init(double[] mat) {
        double[] m = new double[16];
        Arrays.fill(m, 0.0);
        System.arraycopy(mat, 0, m, 0, 12);
        m[15] = 1.0;
        
        matrix = new Matrix4d(m);
        inverseMatrix = (Matrix4d)matrix.clone();
        inverseMatrix.invert();

        unmatrix = new UnMatrix(matrix);        
    }
    
    /**
     * Returns the used matrix of this Transformation. The length of
     * the returned array is 12.
     * 
     * @return double[] matrix array with length 12.
     */
    public double[] getMatrixArray() {
        double[] m = new double[12];
        
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m[pos++] = matrix.getElement(i, j);
            }
        }
        
        return m;
    }
    
    /**
     * Returns the used matrix of this Transformation in an 4x4 double array
     * 
     * @return double[][] used matrix in an 4x4 double array.
     */
    public double[][] getMatrix() {
        double[][] m = new double[4][4];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0;j < 4; j++) {
                m[i][j] = matrix.getElement(i, j);   
            }
        }
        for (int i = 0; i < 3; i++) {
            m[3][i] = 0;
        }
        m[3][3] = 1;
        
        return m;
    }
    
    public AffineTransformation getTranslateTransformation() {
        return new AffineTransformation(unmatrix.getTranslateMatrix());    
    }
    
    public AffineTransformation getRotationTransformation() {
        return new AffineTransformation(unmatrix.getRotationMatrix());    
    }
       
    public AffineTransformation getShearTransformation() {            
        return new AffineTransformation(unmatrix.getShearMatrix());    
    }
    
    public AffineTransformation getScaleTransformation() {
        return new AffineTransformation(unmatrix.getScaleMatrix()); 
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
        
        AffineTransformation t = (AffineTransformation)transform;
        
        Matrix4d m = (Matrix4d)matrix.clone();
        m.mul(t.matrix);

        return new AffineTransformation(m);        
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
     * 
     * 
     * @param rotXYZ die Winkel um die Rotiert werden soll. <code>rx</code>
     *                Rotation um die x-Achse, <code>ry</code> Rotation um die
     *                y-Achse und <code>rz</code> Rotation um die z-Achse.
     *                Dabei wird die Rotation um die z-Achse zuerst ausgeführt. Die
     *                Rotation um die x-Achse kommt zum Schluß. (Kardanwinkel.)
     * 
     */
    public static AffineTransformation getRotateInstance(double rx, double ry, double rz) {
        double[] m = new double[12];
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
        
        return new AffineTransformation(m); 
    }
    
    public static AffineTransformation getScaleInstance(double sx, double sy, double sz) {
        double[] m = new double[12];  
        Arrays.fill(m, 0);
        
        m[0] = sx;
        m[5] = sy;
        m[10] = sz;
        
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
    
    
    private Point3d sourced = new Point3d();
    private Point3d targetd = new Point3d();    
    public void transform(double[] source, double[] target) {
        sourced.set(source);
        matrix.transform(sourced, targetd);
        targetd.get(target);
    }

    private Point3f sourcef = new Point3f();
    private Point3f targetf = new Point3f();
    public void transform(float[] source, float[] target) {
        sourcef.set(source);
        matrix.transform(sourcef, targetf);
        targetf.get(target);
    }


    public void transform(int[] source, int[] target) {
    }
    

    public InterpolateableTransformation interpolate(InterpolateableTransformation trans2, double w) throws IllegalArgumentException {
        if (!(trans2 instanceof AffineTransformation)) {
            throw new IllegalArgumentException("trans2 is not an AffineTransformation: " + 
                                                getClass().getName() + ".interpolate()");   
        }
        
        AffineTransformation t2 = (AffineTransformation)trans2;
                
        AffineTransformation As = interpolateScale(t2, w);
        AffineTransformation Ash = interpolateShear(t2, w);
        AffineTransformation Ar = interpolateRotate(t2, w);  
        AffineTransformation At = interpolateTranslate(t2, w);
        AffineTransformation Ap = interpolatePerspective(t2, w);
                
        return (AffineTransformation)Ap.concatenate(At.concatenate(Ar.concatenate(Ash.concatenate(As))));
    } 
    
    protected AffineTransformation interpolateScale(AffineTransformation transform, double w) {
        return null; 
    }
    
    protected AffineTransformation interpolateShear(AffineTransformation transform, double w) {
        return null;
    }
    
    protected AffineTransformation interpolateRotate(AffineTransformation transform, double w) {
        return null;
    }
    
    protected AffineTransformation interpolateTranslate(AffineTransformation transform, double w) {
        return null;
    }
    
    protected AffineTransformation interpolatePerspective(AffineTransformation transform, double w) {
        Matrix4d m = new Matrix4d();
        m.setElement(0, 0, 1.0);
        m.setElement(1, 1, 1.0);
        m.setElement(2, 2, 1.0);
        m.setElement(3, 3, 1.0);
        return new AffineTransformation(m);
    }

    public void transformBackward(double[] source, double[] target) {

    }

    public void transformBackward(float[] source, float[] target) {

    }

    public void transformBackward(int[] source, int[] target) {

    }



    public String toString() {
        return getClass().getName() + "\n" + matrix.toString();
    }
    

}


