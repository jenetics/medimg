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
public class AffineTransformation implements Transformation {

    protected int DIM;
    protected double[][] trans = new double[4][4];
    protected double[][] inv = new double[4][4];

    
    /*public AffineTransformation() {
        DIM = 3;
    }*/
    /** Creates new AffineTransform */
    public AffineTransformation() {
    	DIM = 3;
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
    public Transformation scale(double alpha) {
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
    public Transformation concatenate(Transformation transform) {
        int i, j;

        AffineTransformation t = (AffineTransformation) transform;
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

    
    public Transformation createInverse() {
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
    
    
    public void transform(Image source, Image target) {
    }
    
    
    /**
     * Transformation of an whole ImageData
     * 
     * @param source transforming ImageData
     * @return target transfromed ImageData
     */
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        int color;
        target.resetColor(255);
        int maxX = target.getMaxX();
        int maxY = target.getMaxY();
        int maxZ = target.getMaxZ();
        int minX = target.getMinX();
        int minY = target.getMinY();
        int minZ = target.getMinZ();        
        double[] tPoint = new double[3];
        double[] sPoint = new double[3];
        int[] tiPoint = new int[3];
        target.resetColor(255);
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; k++) {
                    sPoint[0] = i;
                    sPoint[1] = j;
                    sPoint[2] = k;
                    transformBackward(sPoint, tPoint);
                    tiPoint[0] = (int)Math.round(tPoint[0]);
                    tiPoint[1] = (int)Math.round(tPoint[1]);
                    tiPoint[2] = (int)Math.round(tPoint[2]);                   
                    if (!(tiPoint[0] > maxX || tiPoint[1] > maxY || tiPoint[2] > maxZ || 
                          tiPoint[0] < minX || tiPoint[1] < minY || tiPoint[2] < minZ)) {
                        target.setColor(i, j, k, source.getColor(tiPoint[0], tiPoint[1], tiPoint[2]));
                    }
                }
            }
        }
        return target;
    }
    
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public void transform(double[] source, double[] target) {
        double x = trans[0][0] * source[0] + 
                   trans[0][1] * source[1] +
                   trans[0][2] * source[2] +
                   trans[0][3];

        double y = trans[1][0] * source[0] + 
                   trans[1][1] * source[1] +
                   trans[1][2] * source[2] +
                   trans[1][3];

        double z = trans[2][0] * source[0] + 
                   trans[2][1] * source[1] +
                   trans[2][2] * source[2] +
                   trans[2][3];
        target[0] = x;
        target[1] = y;
        target[2] = z;
    }  
    
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public void transform(float[] source, float[] target) {
        float x = (float)trans[0][0] * source[0] + 
                   (float)trans[0][1] * source[1] +
                   (float)trans[0][2] * source[2] +
                   (float)trans[0][3];

        float y = (float)trans[1][0] * source[0] + 
                   (float)trans[1][1] * source[1] +
                   (float)trans[1][2] * source[2] +
                   (float)trans[1][3];

        float z = (float)trans[2][0] * source[0] + 
                   (float)trans[2][1] * source[1] +
                   (float)trans[2][2] * source[2] +
                   (float)trans[2][3];
        target[0] = x;
        target[1] = y;
        target[2] = z;
    }
    
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public void transform(int[] source, int[] target) {
        float x = (float)trans[0][0] * source[0] + 
                   (float)trans[0][1] * source[1] +
                   (float)trans[0][2] * source[2] +
                   (float)trans[0][3];

        float y = (float)trans[1][0] * source[0] + 
                   (float)trans[1][1] * source[1] +
                   (float)trans[1][2] * source[2] +
                   (float)trans[1][3];

        float z = (float)trans[2][0] * source[0] + 
                   (float)trans[2][1] * source[1] +
                   (float)trans[2][2] * source[2] +
                   (float)trans[2][3];
        target[0] = (int)Math.round(x);
        target[1] = (int)Math.round(y);
        target[2] = (int)Math.round(z);
    }        
    
       /**
     * Transformation Rückwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    private void transformBackward(double[] source, double[] target) {
        double x = inv[0][0] * source[0] + 
                   inv[0][1] * source[1] +
                   inv[0][2] * source[2] +
                   inv[0][3];

        double y = inv[1][0] * source[0] + 
                   inv[1][1] * source[1] +
                   inv[1][2] * source[2] +
                   inv[1][3];

        double z = inv[2][0] * source[0] + 
                   inv[2][1] * source[1] +
                   inv[2][2] * source[2] +
                   inv[2][3];

        target[0] = x;//*f; 
        target[1] = y;//*f; 
        target[2] = z;//*f;
    }  
    
       /**
     * Transformation Rückwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    private void transformBackward(float[] source, float[] target) {
        float x = (float)inv[0][0] * source[0] + 
                   (float)inv[0][1] * source[1] +
                   (float)inv[0][2] * source[2] +
                   (float)inv[0][3];

        float y = (float)inv[1][0] * source[0] + 
                   (float)inv[1][1] * source[1] +
                   (float)inv[1][2] * source[2] +
                   (float)inv[1][3];

        float z = (float)inv[2][0] * source[0] + 
                   (float)inv[2][1] * source[1] +
                   (float)inv[2][2] * source[2] +
                   (float)inv[2][3];

        target[0] = x;//*f; 
        target[1] = y;//*f; 
        target[2] = z;//*f;
    }
    
       /**
     * Transformation Rückwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    private void transformBackward(int[] source, int[] target) {
        float x = (float)inv[0][0] * source[0] + 
                   (float)inv[0][1] * source[1] +
                   (float)inv[0][2] * source[2] +
                   (float)inv[0][3];

        float y = (float)inv[1][0] * source[0] + 
                   (float)inv[1][1] * source[1] +
                   (float)inv[1][2] * source[2] +
                   (float)inv[1][3];

        float z = (float)inv[2][0] * source[0] + 
                   (float)inv[2][1] * source[1] +
                   (float)inv[2][2] * source[2] +
                   (float)inv[2][3];

        target[0] = (int)Math.round(x);//*f; 
        target[1] = (int)Math.round(y);//*f; 
        target[2] = (int)Math.round(z);//*f;
    }     
    
    public void setTransformMatrix(double[][] t) {
        int i, j;
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM + 1; j++) {
                trans[i][j] = t[i][j];
            }
        }
    }

    public double[][] getTransfromMatrix() {
        int i, j;
        double[][] erg = new double[DIM][DIM + 1];
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM; j++) {
                erg[i][j] = trans[i][j];
            }
        }
        return erg;
    }
    
    public String toString() {
       String out = "";
       out += "AffineTransform; Matrix 4 x 4: " + '\n';
       for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM + 1; j++) {
                out += trans[i][j] + "  "; 
            }
            
            out += '\n';
        }
       return out + '\n';
    }        

}
