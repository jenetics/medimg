/**
 * PerspectiveTransformation.java
 * 
 * Created on 17.03.2003, 22:21:37
 *
 */
package org.wewi.medimg.image.geom.transform;


import javax.vecmath.Matrix4d;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PerspectiveTransformation extends AffineTransformation {

    public PerspectiveTransformation(double[] matrix) {
        init(matrix);
    }
    
    public PerspectiveTransformation(double[][] matrix) {
        double[] m = new double[16];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[count] = matrix[i][j];
                ++count;
            }
        }
        init(m);        
    }
    
    public PerspectiveTransformation(PerspectiveTransformation transformation) {
        double[] m = new double[16];
        transformation.matrix.set(m);
        init(m);
    }


    private void init(double[] mat) {
        matrix = new Matrix4d(mat);
        inverseMatrix = new Matrix4d(matrix);
        inverseMatrix.invert();

        unmatrix = new UnMatrix(matrix);        
    }
    
    /**
     * The interpolation of the perspective transformation makes no sens
     * in an affine transformation, so the returned transformation
     * is the identity
     * 
     * @param transform
     * @param w
     * @return AffineTransformation identity transformation.
     */
    protected AffineTransformation interpolatePerspective(AffineTransformation transform, double w) {
        double[] p1 = unmatrix.getPerspectiveParameter();
        double[] p2 = transform.unmatrix.getPerspectiveParameter();
        double[] p3 = new double[4];
        p3[0] = (1.0-w)*p1[0] + w*p2[0];
        p3[1] = (1.0-w)*p1[1] + w*p2[1];
        p3[2] = (1.0-w)*p1[2] + w*p2[2];
        p3[3] = (1.0-w)*p1[3] + w*p2[3];
        
        return new PerspectiveTransformation(UnMatrix.createPerspectiveMatrix(p3[0], p3[1], p3[2], p3[3]));
    }
    
    
   
    public void transform(double[] source, double[] target) {
        target[0] = matrix.m00*source[0] + 
                    matrix.m01*source[1] + 
                    matrix.m02*source[2] + matrix.m03;
        target[1] = matrix.m10*source[0] + 
                    matrix.m11*source[1] + 
                    matrix.m12*source[2] + matrix.m13;
        target[2] = matrix.m20*source[0] + 
                    matrix.m21*source[1] + 
                    matrix.m22*source[2] + matrix.m23;
                    
        double w = matrix.m30*source[0] + 
                    matrix.m31*source[1] + 
                    matrix.m32*source[2] + matrix.m33;
        w = 1.0/w;
        
        target[0] *= w;
        target[1] *= w;
        target[2] *= w;
    }


    public void transform(float[] source, float[] target) {
        target[0] = (float)matrix.m00*source[0] + 
                    (float)matrix.m01*source[1] + 
                    (float)matrix.m02*source[2] + (float)matrix.m03;
        target[1] = (float)matrix.m10*source[0] + 
                    (float)matrix.m11*source[1] + 
                    (float)matrix.m12*source[2] + (float)matrix.m13;
        target[2] = (float)matrix.m20*source[0] + 
                    (float)matrix.m21*source[1] + 
                    (float)matrix.m22*source[2] + (float)matrix.m23;
                    
        float w = (float)matrix.m30*source[0] + 
                   (float)matrix.m31*source[1] + 
                   (float)matrix.m32*source[2] + (float)matrix.m33;
                   
        w = 1.0f/w;
        
        target[0] *= w;
        target[1] *= w;
        target[2] *= w;        
    }


    public void transform(int[] source, int[] target) {
        float w = (float)matrix.m30*source[0] + 
                   (float)matrix.m31*source[1] + 
                   (float)matrix.m32*source[2] + (float)matrix.m33;
                   
        w = 1.0f/w;
        
        target[0] = Math.round(((float)matrix.m00*source[0] + 
                                (float)matrix.m01*source[1] + 
                                (float)matrix.m02*source[2] + (float)matrix.m03)*w);
        target[1] = Math.round(((float)matrix.m10*source[0] + 
                                (float)matrix.m11*source[1] + 
                                (float)matrix.m12*source[2] + (float)matrix.m13)*w);
        target[2] = Math.round(((float)matrix.m20*source[0] + 
                                (float)matrix.m21*source[1] + 
                                (float)matrix.m22*source[2] + (float)matrix.m23)*w);
    }
    
    public void transformBackward(double[] source, double[] target) {
        target[0] = inverseMatrix.m00*source[0] + 
                    inverseMatrix.m01*source[1] + 
                    inverseMatrix.m02*source[2] + inverseMatrix.m03;
        target[1] = inverseMatrix.m10*source[0] + 
                    inverseMatrix.m11*source[1] + 
                    inverseMatrix.m12*source[2] + inverseMatrix.m13;
        target[2] = inverseMatrix.m20*source[0] + 
                    inverseMatrix.m21*source[1] + 
                    inverseMatrix.m22*source[2] + inverseMatrix.m23;
                    
        double w = inverseMatrix.m30*source[0] + 
                    inverseMatrix.m31*source[1] + 
                    inverseMatrix.m32*source[2] + inverseMatrix.m33;
        w = 1.0/w;
        
        target[0] *= w;
        target[1] *= w;
        target[2] *= w;                    
    }

    public void transformBackward(float[] source, float[] target) {
        target[0] = (float)inverseMatrix.m00*source[0] + 
                    (float)inverseMatrix.m01*source[1] + 
                    (float)inverseMatrix.m02*source[2] + (float)inverseMatrix.m03;
        target[1] = (float)inverseMatrix.m10*source[0] + 
                    (float)inverseMatrix.m11*source[1] + 
                    (float)inverseMatrix.m12*source[2] + (float)inverseMatrix.m13;
        target[2] = (float)inverseMatrix.m20*source[0] + 
                    (float)inverseMatrix.m21*source[1] + 
                    (float)inverseMatrix.m22*source[2] + (float)inverseMatrix.m23;
                    
        float w = (float)inverseMatrix.m30*source[0] + 
                   (float)inverseMatrix.m31*source[1] + 
                   (float)inverseMatrix.m32*source[2] + (float)inverseMatrix.m33;
                   
        w = 1.0f/w;
        
        target[0] *= w;
        target[1] *= w;
        target[2] *= w;                    
    }

    public void transformBackward(int[] source, int[] target) {
        float w = (float)inverseMatrix.m30*source[0] + 
                   (float)inverseMatrix.m31*source[1] + 
                   (float)inverseMatrix.m32*source[2] + (float)inverseMatrix.m33;
                   
        w = 1.0f/w;
        
        target[0] = Math.round(((float)inverseMatrix.m00*source[0] + 
                                (float)inverseMatrix.m01*source[1] + 
                                (float)inverseMatrix.m02*source[2] + (float)inverseMatrix.m03)*w);
        target[1] = Math.round(((float)inverseMatrix.m10*source[0] + 
                                (float)inverseMatrix.m11*source[1] + 
                                (float)inverseMatrix.m12*source[2] + (float)inverseMatrix.m13)*w);
        target[2] = Math.round(((float)inverseMatrix.m20*source[0] + 
                                (float)inverseMatrix.m21*source[1] + 
                                (float)inverseMatrix.m22*source[2] + (float)inverseMatrix.m23)*w);
    }    
}
