/*
 * TransformInterpol.java
 *
 * Created on 23. März 2002, 12:30
 */

package org.wewi.medimg.reg.interpolation;

import org.wewi.medimg.image.geom.transform.Transformation;
//import org.wewi.medimg.image.geom.transform.AffineInterpolation;
//import org.wewi.medimg.reg.TransformVector;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class TransformInterpol implements InterpolStrategy {
    
    protected double errorLimit;
    protected double[] transWeights;
    //protected TransformVector transformVector;
    
    /** Creates new TransformInterpol */
    public TransformInterpol() {
        transWeights = null;
        errorLimit = 0.5;
    }
    
    public void setErrorLimit(double limit) {
        errorLimit = limit;
    }
    
    public double getErrorLimit() {
        return errorLimit;
    }
    

    
    public Transformation interpolate() {
        /*
        AffineInterpolation erg = new AffineInterpolation();
        AffineInterpolation transTemp;
        transformVector = tv;
        int transforms = transformVector.size();
        transWeights = new double[transforms];
        calculateWeights();

        double[][] rot = new double[3][3];
        double[][] transScale = new double[3][4];
        double[][] tempRot = new double[3][3];
        double[][] tempTransScale = new double[3][4];

        int i, j, k;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                rot[i][j] = 0.0;
                tempRot[i][j] = 0.0;
            }
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 4; j++) {
                transScale[i][j] = 0.0;
                tempTransScale[i][j] = 0.0;
            }
        }

        for (i = 0; i < transforms; i++) {
            transTemp = (AffineInterpolation)(tv.getTransformByPos(i));
            //std::cout << "Transform: " << i << std::endl;
            System.out.println(transTemp);
            if (transWeights[i] == 0) {
                continue;
            }
            tempRot = transTemp.getRotationMatrix();
            for (j = 0; j < 3; j++) {
                for (k = 0; k < 3; k++) {
                    rot[j][k] += tempRot[j][k]*transWeights[i];
                }
            }
            tempTransScale = transTemp.getTransScalingMatrix();
            for (j = 0; j < 3; j++) {
                for (k = 0; k < 4; k++) {
                    transScale[j][k] += tempTransScale[j][k]*transWeights[i];
                }
            }
        }

        //unifying the rotation matrix
        double norm = 0.0;
        for (i = 0; i < 3; i++) {
            norm = 1.0/Math.sqrt(rot[0][i]*rot[0][i] +
                            rot[1][i]*rot[1][i] +
                            rot[2][i]*rot[2][i]);
            rot[0][i] = rot[0][i]*norm;
            rot[1][i] = rot[1][i]*norm;
            rot[2][i] = rot[2][i]*norm;
        }
            //Zum Test kann eine eigene Transformationsmatrix angegeben werden
        /*transScale[0][0] = 1;
        transScale[0][1] = 0;
        transScale[0][2] = 0;
        transScale[0][3] = 0;

        transScale[1][0] = 0;
        transScale[1][1] = 1;
        transScale[1][2] = 0;
        transScale[1][3] = 0;

        transScale[2][0] = 0;
        transScale[2][1] = 0;
        transScale[2][2] = 1.0;
        transScale[2][3] = 0;*/
        //erg.setTransformation(rot, transScale);
        //return erg;
        return null;
    }
    
    public void calculateWeights() {
    }
}
