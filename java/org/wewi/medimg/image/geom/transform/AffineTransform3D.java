/*
 * AffineTransform3D.java
 *
 * Created on 23. März 2002, 10:28
 */

package org.wewi.medimg.image.geom.transform;


import org.wewi.medimg.image.Image;
import org.wewi.medimg.util.Timer;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class AffineTransform3D extends AffineTransform {

    /** Creates new AffineTransform3D */
    public AffineTransform3D() {
        DIM = 3;
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
    
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public double[] transform_fw(double[] source) {
        double[] target = new double[DIM];
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

        /*double w = trans[3][0] * source[0] + 
                   trans[3][1] * source[1] +
                   trans[3][2] * source[2] +
                   trans[3][3];

        double f = 1.0/w;*/
        target[0] = x;//*f; 
        target[1] = y;//*f; 
        target[2] = z;//*f;
        return target;
    }  
    
       /**
     * Transformation Rückwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public double[] transform_bw(double[] source) {
        double[] target = new double[DIM];
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

        /*double w = inv[3][0] * source[0] + 
                   inv[3][1] * source[1] +
                   inv[3][2] * source[2] +
                   inv[3][3];

        double f = 1.0/w;*/
        target[0] = x;//*f; 
        target[1] = y;//*f; 
        target[2] = z;//*f;
        return target;
    }  
    
    /**
     * Transformation of an whole ImageData
     * 
     * @param source transforming ImageData
     * @return target transfromed ImageData
     */
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        int i, j, k, color;
        int maxX = target.getMaxX();
        int maxY = target.getMaxY();
        int maxZ = target.getMaxZ();
        int minX = target.getMinX();
        int minY = target.getMinY();
        int minZ = target.getMinZ();        
        //System.out.println(" X " + maxX + " Y " + maxY + " Z " + maxZ);
        double[] point_s = new double[3];
        double[] point_t = new double[3];
        Timer timer1 = new Timer("C3DAffineTransform::transform1");
        timer1.start();
        target.resetColor(255);
        for (i = minX; i <= maxX; i++) {
            for (j = minY; j <= maxY; j++) {
                for (k = minZ; k <= maxZ; k++) {
                    point_s[0] = i;
                    point_s[1] = j;
                    point_s[2] = k;
                    color = source.getColor(i, j, k);
                    point_t = transform_fw(point_s);
                    if (!((int)point_t[0] > maxX || (int)point_t[1] > maxY || (int)point_t[2] > maxZ || 
                          (int)point_t[0] < minX || (int)point_t[1] < minY || (int)point_t[2] < minZ)) {
                        target.setColor((int)point_t[0], (int)point_t[1], (int)point_t[2], color);
                    }
                }
            }
        }
        timer1.stop();
        timer1.print();
        return target;
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
