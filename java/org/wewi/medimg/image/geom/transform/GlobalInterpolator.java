/**
 * GlobalInterpolator.java
 * 
 * Created on 07.03.2003, 18:06:47
 *
 */
package org.wewi.medimg.image.geom.transform;
import java.util.Arrays;

import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.vec.VectorIterator;

/**
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GlobalInterpolator extends DisplacementF.Interpolator {
    
    public static interface WeightFunction extends Cloneable {
        
        public double eval(double[] p, double[] q);
        
        public Object clone();
    }
    
    
    public final static class ExponentialWeightFunction implements WeightFunction {

        private double b = 1;
        
        public ExponentialWeightFunction(double b) {
            this.b = b;    
        }
        
		public double eval(double[] p, double[] q) {
            
            double d = Math.sqrt(MathUtil.sqr(p[0] - q[0]) + 
                                  MathUtil.sqr(p[1] - q[1]) +
                                  MathUtil.sqr(p[2] - q[2]));
			//return Math.exp(-d*b);
            
            //if (d < 100) {
                return 1/(d+1);
            //} else {
            //    return 0;
            //}
            //return 1000-d*d;
		}
        
		public Object clone() {
			return new ExponentialWeightFunction(this.b);
		}
    }
    
  
    private WeightFunction weightFunction;
    
    private GlobalInterpolator(GlobalInterpolator gi) {
        this((WeightFunction)gi.weightFunction.clone());
    }
    
    public GlobalInterpolator() {
        this.weightFunction = new ExponentialWeightFunction(1);
    }
    
    public GlobalInterpolator(WeightFunction weightFunction) {
        this.weightFunction = weightFunction;
    }
    
    public void setWeightFunction(WeightFunction weightFunction) {
        this.weightFunction = (WeightFunction)weightFunction.clone();
    }
    
    public WeightFunction getWeightFunction() {
        return weightFunction;
    }
    
    
	public void interpolateEndPoint(double[] startPoint, double[] endPoint) {
        double[] start = new double[3];
        double[] end = new double[3];
        double[] vector = new double[3];
        
        Arrays.fill(vector, 0);
        
        double w = 1;
        double wsum = 0;
        for (VectorIterator it = getField().getVectorIterator(); it.hasNext();) {
            it.next(start, end);

            w = weightFunction.eval(start, startPoint);
            
            if (Double.isInfinite(w)) {
                System.arraycopy(end, 0, endPoint, 0, 3);
                return; 
            }
            
            vector[0] += (end[0] - start[0])*w;
            vector[1] += (end[1] - start[1])*w;
            vector[2] += (end[2] - start[2])*w;
            
            wsum += w;
        }
        
        vector[0] /= wsum;
        vector[1] /= wsum;
        vector[2] /= wsum;
        
        endPoint[0] = startPoint[0] + vector[0];
        endPoint[1] = startPoint[1] + vector[1];
        endPoint[2] = startPoint[2] + vector[2];
        
	}
    
	public void interpolateStartPoint(double[] endPoint, double[] startPoint) {
        double[] start = new double[3];
        double[] end = new double[3];
        double[] vector = new double[3];
        
        Arrays.fill(vector, 0);
        
        double w = 1;
        double wsum = 0;
        for (VectorIterator it = getField().getVectorIterator(); it.hasNext();) {
            it.next(start, end);

            w = weightFunction.eval(end, endPoint);
 
            if (Double.isInfinite(w)) {
                //System.arraycopy(start, 0, startPoint, 0, 3);
                //return; 
            }
            
            vector[0] += (end[0] - start[0])*w;
            vector[1] += (end[1] - start[1])*w;
            vector[2] += (end[2] - start[2])*w;
            
            wsum += w;
        }
        
        //System.out.println("wsum: " + wsum);
        
        vector[0] /= wsum;
        vector[1] /= wsum;
        vector[2] /= wsum;
        
        
        startPoint[0] = endPoint[0] - vector[0];
        startPoint[1] = endPoint[1] - vector[1];
        startPoint[2] = endPoint[2] - vector[2];        
	}
    
    
    
    public Object clone() {
        return new GlobalInterpolator(this);
    }
}








