/**
 * GlobalInterpolator.java
 * 
 * Created on 07.03.2003, 18:06:47
 *
 */
package org.wewi.medimg.image.geom.transform;
import java.util.Arrays;

import org.wewi.medimg.math.vec.VectorIterator;

/**
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GlobalInterpolator implements DisplacementF.Interpolator {
    
    public static interface WeightFunction {
        public double eval(double[] p, double[] q);
    }
    
    
    private DisplacementF field;
    private WeightFunction weight;
    
    
    public GlobalInterpolator(DisplacementF field, WeightFunction weightFunction) {
        weight = weightFunction;
        this.field = field;
    }
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateEndPoint(int[], int[])
	 */
	public void interpolateEndPoint(int[] startPoint, int[] endPoint) {
        interpolateEndPoint(startPoint, endPoint);
	}
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateEndPoint(float[], float[])
	 */
	public void interpolateEndPoint(float[] startPoint, float[] endPoint) {
        interpolateEndPoint(startPoint, endPoint);
	}
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateEndPoint(double[], double[])
	 */
	public void interpolateEndPoint(double[] startPoint, double[] endPoint) {
        double[] start = new double[3];
        double[] end = new double[3];
        double[] vector = new double[3];
        
        Arrays.fill(vector, 0);
        
        double w = 1;
        int count = 0;
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);

            w = weight.eval(start, startPoint);
            
            vector[0] += (end[0] - start[0])*w;
            vector[1] += (end[1] - start[1])*w;
            vector[2] += (end[2] - start[2])*w;
            
            count++;
        }
        
        vector[0] /= (double)count;
        vector[1] /= (double)count;
        vector[2] /= (double)count;
        
        endPoint[0] = startPoint[0] + vector[0];
        endPoint[1] = startPoint[1] + vector[1];
        endPoint[2] = startPoint[2] + vector[2];
        
	}
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateStartPoint(int[], int[])
	 */
	public void interpolateStartPoint(int[] endPoint, int[] startPoint) {
        interpolateStartPoint(endPoint, startPoint);
	}
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateStartPoint(float[], float[])
	 */
	public void interpolateStartPoint(float[] endPoint, float[] startPoint) {
        interpolateStartPoint(endPoint, startPoint);
	}
    
	/**
	 * @see org.wewi.medimg.image.geom.transform.DisplacementF.Interpolator#interpolateStartPoint(double[], double[])
	 */
	public void interpolateStartPoint(double[] endPoint, double[] startPoint) {
        double[] start = new double[3];
        double[] end = new double[3];
        double[] vector = new double[3];
        
        Arrays.fill(vector, 0);
        
        double w = 1;
        int count = 0;
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);

            w = weight.eval(end, endPoint);
            
            vector[0] += (start[0] - end[0])*w;
            vector[1] += (start[1] - end[0])*w;
            vector[2] += (start[2] - end[0])*w;
            
            count++;
        }
        
        vector[0] /= (double)count;
        vector[1] /= (double)count;
        vector[2] /= (double)count;
        
        endPoint[0] = startPoint[0] + vector[0];
        endPoint[1] = startPoint[1] + vector[1];
        endPoint[2] = startPoint[2] + vector[2];        
	}
}








