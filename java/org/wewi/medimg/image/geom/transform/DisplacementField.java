/**
 * DisplacementF.java
 * 
 * Created on 07.03.2003, 14:02:42
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.math.vec.VectorField;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class DisplacementField extends ImageTransformation implements VectorField {

    public static abstract class Interpolator implements Cloneable {
        private DisplacementField field;
        
        
        private void setField(DisplacementField field) {
            this.field = field;
        }
        
        public DisplacementField getField() {
            return field;
        }
        
        
        private double[] start = new double[3];
        private double[] end = new double[3];
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */
        public void interpolateEndPoint(int[] startPoint, int[] endPoint) {
            start[0] = startPoint[0];
            start[1] = startPoint[1];
            start[2] = startPoint[2];
            
            interpolateEndPoint(start, end);
            
            endPoint[0] = (int)Math.round(end[0]);
            endPoint[1] = (int)Math.round(end[1]);
            endPoint[2] = (int)Math.round(end[2]);
        }
        
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */        
        public void interpolateEndPoint(float[] startPoint, float[] endPoint) {
            start[0] = startPoint[0];
            start[1] = startPoint[1];
            start[2] = startPoint[2];
            
            interpolateEndPoint(start, end);
            
            endPoint[0] = (float)end[0];
            endPoint[1] = (float)end[1];
            endPoint[2] = (float)end[2];            
        }
        
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */        
        public abstract void interpolateEndPoint(double[] startPoint, double[] endPoint); 
        
        /**
         * Interpolating the start-point from a given end-point.
         * The end-pont is not necessarily a end-point from a vector
         * in the vector field. This method is logical equvalent to an
         * inverse transformation of an end point.
         * 
         * @param endPoint the end-point from which you want to 
         *           interpolate the start-point.
         * @param startPoint the interpolated start-point.
         */
        public void interpolateStartPoint(int[] endPoint, int[] startPoint) {
            end[0] = endPoint[0];
            end[1] = endPoint[1];
            end[2] = endPoint[2];
            
            interpolateEndPoint(end, start);
            
            startPoint[0] = (int)Math.round(start[0]);
            startPoint[1] = (int)Math.round(start[1]);
            startPoint[2] = (int)Math.round(start[2]);
        }
        
        /**
         * Interpolating the start-point from a given end-point.
         * The end-pont is not necessarily a end-point from a vector
         * in the vector field. This method is logical equvalent to an
         * inverse transformation of an end point.
         * 
         * @param endPoint the end-point from which you want to 
         *           interpolate the start-point.
         * @param startPoint the interpolated start-point.
         */        
        public void interpolateStartPoint(float[] endPoint, float[] startPoint) {
            end[0] = endPoint[0];
            end[1] = endPoint[1];
            end[2] = endPoint[2];
            
            interpolateEndPoint(end, start);
            
            startPoint[0] = (float)start[0];
            startPoint[1] = (float)start[1];
            startPoint[2] = (float)start[2];
        }
        
        /**
         * Interpolating the start-point from a given end-point.
         * The end-pont is not necessarily a end-point from a vector
         * in the vector field. This method is logical equvalent to an
         * inverse transformation of an end point.
         * 
         * @param endPoint the end-point from which you want to 
         *           interpolate the start-point.
         * @param startPoint the interpolated start-point.
         */        
        public abstract void interpolateStartPoint(double[] endPoint, double[] startPoint); 
        
        public abstract Object clone();  
    }
    
    
    
    
    
    private Interpolator interpolator;
    
    protected DisplacementField() {    
    }
    
    
    public void setFieldInterpolator(Interpolator interpolator) {
        this.interpolator = (Interpolator)interpolator.clone();
        this.interpolator.setField(this);
    }
    
    public Interpolator getFieldInterpolator() {
        return interpolator;
    }    
    
    public void transform(int[] source, int[] target) {
        interpolator.interpolateEndPoint(source, target);    
    }

    public void transform(float[] source, float[] target) {
        interpolator.interpolateEndPoint(source, target);
    }

    public void transform(double[] source, double[] target) {
        interpolator.interpolateEndPoint(source, target);
    }
    
    public void transformBackward(int[] target, int[] source) {
        interpolator.interpolateStartPoint(target, source);
    }
    
    public void transformBackward(float[] target, float[] source) {
        interpolator.interpolateStartPoint(target, source);
    }
    
    public void transformBackward(double[] target, double[] source) {
        interpolator.interpolateStartPoint(target, source);
    }

}
