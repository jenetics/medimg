/*
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;


/**
 *
 * @author  werner weiser
 * @version 
 */
public class AffineTransform implements Transform {

    protected int DIM;
    protected double[][] rot = new double[3][3];
    protected double[][] transScale = new double[3][4];
    protected double[][] trans = new double[4][4];
    protected double[][] inv = new double[4][4];

    /** Creates new AffineTransform */
    protected AffineTransform() {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                trans[i][j] = 0.0;
            }
        }
        trans[3][3] = 1.0; 
    }

    /**
     * Setting the element of the transformation matrix
     *
     * @param i row of the matrix
     * @param j column of the matrix
     * @param value to be set
     */
    public void setElement(int i, int j, double value) {
        trans[i][j] = value;
    }
    /**
     * Getting the element of the transformation matrix
     *
     * @param i row of the matrix
     * @param j column of the matrix
     *
     * @return value of the matrix element
     */
    public double getElement(int i, int j) {
        return trans[i][j];
    }

    /**
     * Scaling the transformation matrix
     *
     * @param alpha scaling factor
     */
    public Transform scale(double alpha) {
        int i, j;
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                trans[i][j] *= alpha;
            }
        }
        return this;
    }

   
    /**
     * Concatenating two transformations
     *
     * @param transform concatenating transformation
     *
     * \todo Funktioniert zur Zeit nur, wenn transform vom Typ C3DAffineTransform ist!
     *       Produziert auch eine Compilerwarnung.
     */
    public Transform concatenate(Transform transform) {
        int i, j;

        AffineTransform t = (AffineTransform) transform;
        DoubleFactory2D factory;
        factory = DoubleFactory2D.dense;
        DoubleMatrix2D matrix1 = factory.make(DIM+1,DIM+1);
        DoubleMatrix2D matrix2 = factory.make(DIM+1,DIM+1);
        DoubleMatrix2D erg = factory.make(DIM+1,DIM+1);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                matrix1.setQuick(i, j, t.getElement(i, j));
                matrix2.setQuick(i, j, getElement(i, j));
            }
        }
        matrix1.setQuick(DIM, DIM, 1.0);
        matrix2.setQuick(DIM, DIM, 1.0);
        Algebra alg = new Algebra();
        erg = alg.mult(matrix1, matrix2);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                setElement(i, j, erg.getQuick(i, j));
            }
        }        
        return this;
    }

    
    public Transform invert() {
        int size = DIM+1;
        int i, j;
        
        // Vielleicht LU Decomposition
        DoubleFactory2D factory;
        factory = DoubleFactory2D.dense;
        DoubleMatrix2D matrix1 = factory.make(DIM+1,DIM+1);
        DoubleMatrix2D inv = factory.make(DIM+1,DIM+1);
        DoubleMatrix2D erg = factory.make(DIM+1,DIM+1);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                matrix1.setQuick(i, j, getElement(i, j));
            }
        }
        matrix1.setQuick(DIM, DIM, 1.0);
        Algebra alg = new Algebra();
        inv = alg.inverse(matrix1);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                setElement(i, j, inv.getQuick(i, j));
            }
        }
        return this;
    }
    
    public String toString() { 
        return null;
    }
    
    public Image transform(Image source) {
        return null;
    }
    
    public double[] transform_fw(double[] source) {
        return null;
    }
    
    public double[] transform_bw(double[] source) {
        return null;
    }
   
    public void transformBackward(double[] source, double[] target) {
    }
    
    public void transformBackward(float[] source, float[] target) {
    }
    
    public void transformForward(double[] source, double[] target) {
    }
    
    public void transformForward(float[] source, float[] target) {
    }    
    
    /* weitere Methoden
     *
     *virtual const double getElement(const int i, const int j);

    virtual void setElement(const int i, const int j, const double value);

    virtual void setRotationMatrix(const double rot[3][3]) = 0;

    virtual void getRotationMatrix(double rot[3][3]) = 0;

    virtual void setTransScalingMatrix(const double ts[3][4]) = 0;

    virtual void getTransScalingMatrix(double ts[3][4]) = 0;

    virtual void setTransformMatrix(const double trans[3][4]) = 0;

    virtual void getTransfromMatrix(double trans[3][4]) = 0;

    virtual void setTransformation(const double rot[3][3], const double ts[3][4]) = 0;

     */
    
    
}
