/*
 * AffineTransform3D.java
 *
 * Created on 23. März 2002, 10:28
 */

package org.wewi.medimg.image.geom.transform;


import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;


/**
 *
 * @author  werner weiser
 * @version 
 */
public class AffineInterpolation extends AffineTransformation {

    protected double[][] rot = new double[3][3];
    protected double[][] transScale = new double[3][4];
    
    /** Creates new AffineTransform3D */
    public AffineInterpolation() {
    	DIM = 3;
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                trans[i][j] = 0.0;
            }
        }
        trans[3][3] = 1.0; 
    }
    

    public void setRotationMatrix( double[][] r) {
        int i, j;
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM; j++) {
                rot[i][j] = r[i][j];
            }
        }
    }

    public double[][] getRotationMatrix() {
        int i, j;
        double[][] erg = new double[DIM][DIM];
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM; j++) {
                erg[i][j] = rot[i][j];
            }
        }
        return erg;
    }

    public void setTransScalingMatrix(double[][] ts) {
        int i, j;
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM + 1; j++) {
                transScale[i][j] = ts[i][j];
            }
        }
    }

    public double[][] getTransScalingMatrix() {
        int i, j;
        double[][] erg = new double[DIM][DIM + 1];
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM + 1; j++) {
                erg[i][j] = transScale[i][j];
            }
        }
        return erg;
    }
    
    public void setTransformation(double[][] r, double[][] ts) {
        int i, j;
        setRotationMatrix(r);
        setTransScalingMatrix(ts);

        trans[0][0] = ts[0][0]*r[0][0]+ts[0][1]*r[1][0]+ts[0][2]*r[2][0];
        trans[1][0] = ts[1][0]*r[0][0]+ts[1][1]*r[1][0]+ts[1][2]*r[2][0];
        trans[2][0] = ts[2][0]*r[0][0]+ts[2][1]*r[1][0]+ts[2][2]*r[2][0];

        trans[0][1] = ts[0][0]*r[0][1]+ts[0][1]*r[1][1]+ts[0][2]*r[2][1];
        trans[1][1] = ts[1][0]*r[0][1]+ts[1][1]*r[1][1]+ts[1][2]*r[2][1];
        trans[2][1] = ts[2][0]*r[0][1]+ts[2][1]*r[1][1]+ts[2][2]*r[2][1];

        trans[0][2] = ts[0][0]*r[0][2]+ts[0][1]*r[1][2]+ts[0][2]*r[2][2];
        trans[1][2] = ts[1][0]*r[0][2]+ts[1][1]*r[1][2]+ts[1][2]*r[2][2];
        trans[2][2] = ts[2][0]*r[0][2]+ts[2][1]*r[1][2]+ts[2][2]*r[2][2];

        trans[0][3] = ts[0][3];
        trans[1][3] = ts[1][3];
        trans[2][3] = ts[2][3];
        
        // Setzen der Inversen
        
        DoubleFactory2D factory;
        factory = DoubleFactory2D.dense;
        DoubleMatrix2D matrix1 = factory.make(DIM+1,DIM+1);
        DoubleMatrix2D inverse = factory.make(DIM+1,DIM+1);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                matrix1.setQuick(i, j, getElement(i, j));
            }
        }
        matrix1.setQuick(DIM, DIM, 1.0);
        Algebra alg = new Algebra();
        inverse = alg.inverse(matrix1);
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM+1; j++) {
                inv[i][j] = inverse.getQuick(i, j);
            }
        }
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
    public void printRot() {       
       String out = "";
       out += "AffineTransform; RotationMatrix 4 x 4:" + '\n';
       for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                out +=  rot[i][j] + "  "; 
            }
            out +=  '\n';
        }
        System.out.println(out + '\n');
    }
        
    public void printScale() {      
        String out = "";
        out += "AffineTransform; TranslationScaleMatrix 4 x 4:" + '\n';
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM + 1; j++) {
                out += transScale[i][j] + "  "; 
            }
            out += '\n';
        }
        System.out.println(out + '\n');        
    }
}
