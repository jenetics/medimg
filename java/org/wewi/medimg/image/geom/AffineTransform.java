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

import java.util.Arrays;


/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public class AffineTransform implements Transform {
    private double[] rotMatrix = new double[9];
    private double[] transScaleMatrix = new double[12];
    public double[] matrix = new double[12];
    public double[] inverseMatrix = new double[12];
    
    
    public AffineTransform(AffineTransform transform) {
        System.arraycopy(transform.rotMatrix, 0, rotMatrix, 0, 9);
        System.arraycopy(transform.transScaleMatrix, 0, transScaleMatrix, 0, 12);
        System.arraycopy(transform.matrix, 0, matrix, 0, 12);
        inverseMatrix = invert(matrix);
    }
     
    public AffineTransform(double[] matrix) {
        System.arraycopy(matrix, 0, this.matrix, 0, 12);
        Arrays.fill(rotMatrix, 0);
        Arrays.fill(transScaleMatrix, 0);
        inverseMatrix = invert(matrix);
        
    }
    
    public AffineTransform(double[] rotMatrix, double[] transScaleMatrix) {
        System.arraycopy(rotMatrix, 0, this.rotMatrix, 0, 9);
        System.arraycopy(transScaleMatrix, 0, this.transScaleMatrix, 0, 9);
      
        DoubleMatrix2D rm = new DenseDoubleMatrix2D(4, 4);
        DoubleMatrix2D tm = new DenseDoubleMatrix2D(4, 4);
        rm.assign(0);
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rm.setQuick(i, j, rotMatrix[pos++]);
            }
        }
        rm.setQuick(3, 3, 1);
        pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                rm.setQuick(i, j, transScaleMatrix[pos++]);
            }
        } 
        tm.setQuick(3, 3, 1);
        DoubleMatrix2D c = rm.zMult(tm, null, 1, 0, false, false);
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[pos++] = c.getQuick(i, j);
            }
        }
        inverseMatrix = invert(matrix);
    }    

    public double[] getRotationMatrix() {
        double[] rot = new double[9];
        System.arraycopy(rotMatrix, 0, rot, 0, 9);
        return rot;
    }
    
    public double[] getTransScaleMatrix() {
        double[] tsm = new double[12];
        System.arraycopy(transScaleMatrix, 0, tsm, 0, 12);
        return tsm;
    }

    public double[] getMatrix() {
        double[] m = new double[12];
        System.arraycopy(matrix, 0, m, 0, 9);
        return m;
    }

    public Transform scale(double alpha) {
        AffineTransform t = new AffineTransform(this);
        for (int i = 0; i < 12; i++) {
            t.matrix[i] *= alpha;
        }
        return t;
    }

    public Transform concatenate(Transform transform) {
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
    
    public Transform invert() {
        return new AffineTransform(inverseMatrix);
    }
    
    public Image transform(Image source) {
        return source;
    }
    
    public void transform(Image source, Image target) {
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
        String string = Double.toString(number);
        string += "0000000000000000000000000000000000000000000000000000000000";
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
    
    /*
    public static void main(String[] args) {
        double[] m = new double[12];
        Arrays.fill(m, 0);
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        AffineTransform t = new AffineTransform(m);
        System.out.println(t);
    }
    */
}
