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

    public static interface Interpolator {
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */
        public void interpolateEndPoint(int[] startPoint, int[] endPoint);
        
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */        
        public void interpolateEndPoint(float[] startPoint, float[] endPoint);
        
        /**
         * Interpolating the end-point from a given start-point.
         * The start-point is not necessarily a start-point from
         * a vector in the vector field.
         * 
         * @param startPoint the start-point from which you want
         *          to interpolate the end-point.
         * @param endPoint the interpolated end-point
         */        
        public void interpolateEndPoint(double[] startPoint, double[] endPoint); 
        
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
        public void interpolateStartPoint(int[] endPoint, int[] startPoint);
        
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
        public void interpolateStartPoint(float[] endPoint, float[] startPoint);
        
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
        public void interpolateStartPoint(double[] endPoint, double[] startPoint);   
    }
    
    
    protected Interpolator interpolator;
    
    
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
    private class TransformTask extends ImageLoop.Task {
		private Image source;
        
        public TransformTask(Image source) {
            this.source = source;
        }
        
        int[] sourcePixel = new int[3];
        int[] targetPixel = new int[3];
		public void execute(int x, int y, int z) {
            targetPixel[0] = x;
            targetPixel[1] = y;
            targetPixel[2] = z;
            
            interpolator.interpolateStartPoint(targetPixel, sourcePixel);
            
            getImage().setColor(x, y, z, source.getColor(sourcePixel[0], sourcePixel[1], sourcePixel[2]));
		}   
    }
    
    
    
    
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }
    
    public Interpolator getInterpolator() {
        return interpolator;
    }

}
