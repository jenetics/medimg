/*
 * AffineTransform.java
 *
 * Created on 21. März 2002, 13:50
 */

package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.Image;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;



/**
 *
 * @author Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public class AffineTransformation implements Transformation {
    private double[] matrix;
    private double[] inverseMatrix;
    
    private AffineTransformation(double[] matrix, double[] inverseMatrix) {
    	matrix = new double[12];
    	inverseMatrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        System.arraycopy(inverseMatrix, 0, this.inverseMatrix, 0, 12);
    }
    
    public AffineTransformation(AffineTransformation transform) {
    	matrix = new double[12];
    	inverseMatrix = new double[12];    	
        System.arraycopy(transform.matrix, 0, matrix, 0, 12);
        System.arraycopy(transform.inverseMatrix, 0, inverseMatrix, 0, 12);
    }
     
    public AffineTransformation(double[] matrix) {
    	matrix = new double[12];
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        inverseMatrix = invert(matrix);
        
    }

    public double[] getMatrix() {
        double[] m = new double[12];
        System.arraycopy(matrix, 0, m, 0, 9);
        return m;
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
    
    public void transform(Image source, Image target) {
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
        
        return buffer.toString();
    }    
    
}
