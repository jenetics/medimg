/**
 * DisplacementF.java
 * 
 * Created on 07.03.2003, 14:02:42
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.ImageLoop;
import org.wewi.medimg.math.vec.VectorField;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class DisplacementF implements Transformation, VectorField {

    public static abstract class Interpolator implements Cloneable {
        private DisplacementF field;
        
        
        private void setField(DisplacementF field) {
            this.field = field;
        }
        
        public DisplacementF getField() {
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
    
    protected DisplacementF() {    
    }
    
    
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = (Interpolator)interpolator.clone();
        interpolator.setField(this);
    }
    
    public Interpolator getInterpolator() {
        return interpolator;
    }    
    
    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#transform(int, int)
     */
    public void transform(int[] source, int[] target) {
        interpolator.interpolateEndPoint(source, target);    
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#transform(float, float)
     */
    public void transform(float[] source, float[] target) {
        interpolator.interpolateEndPoint(source, target);
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#transform(double, double)
     */
    public void transform(double[] source, double[] target) {
        interpolator.interpolateEndPoint(source, target);
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#transform(org.wewi.medimg.image.Image)
     */
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        target.resetColor(0);
        transform(source, target);
 
        return target;
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#transform(org.wewi.medimg.image.Image, org.wewi.medimg.image.Image)
     */
    public void transform(Image source, Image target) {
        ImageLoop loop = new ImageLoop(target, new TransformTask(source));
        loop.loop();
    } 
    private final class TransformTask extends ImageLoop.Task {
		private Image source;
        
        public TransformTask(Image source) {
            this.source = source;
            
        }
        
        private double[] sourcePixel = new double[3];
        private double[] targetPixel = new double[3];
        private int sx, sy, sz;
		public final void execute(int x, int y, int z) {
            targetPixel[0] = x;
            targetPixel[1] = y;
            targetPixel[2] = z;
            
            interpolator.interpolateStartPoint(targetPixel, sourcePixel);
            sx = (int)sourcePixel[0];
            sy = (int)sourcePixel[1];
            sz = (int)sourcePixel[2];
            
            
            if (sx < source.getMinX() || sx > source.getMaxX() ||
                sy < source.getMinY() || sy > source.getMaxY() ||
                sz < source.getMinZ() || sz > source.getMaxZ()) {
                    
                getImage().setColor(x, y, z, 0);
            } else {
                getImage().setColor(x, y, z, source.getColor(sx, sy, sz));
            }
            
            
		}   
    }

}
