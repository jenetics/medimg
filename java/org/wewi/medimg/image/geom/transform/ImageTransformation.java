/**
 * ImageTransformation.java
 * 
 * Created on 17.03.2003, 18:57:44
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.ImageLoop;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageTransformation {
    
    public static abstract class Interpolator {
        private Transformation transformation;
        private Image sourceImage;
        
        
        private void setTransformation(Transformation transformation){
            this.transformation = transformation;
        }
        
        public Transformation getTransformation() {
            return transformation;
        }
        
        private void setSourceImage(Image sourceImage) {
            this.sourceImage = sourceImage;
        }
        
        public Image getSourceImage() {
            return sourceImage;        
        }
        
        /**
         * This method calculates the (interpolated) color of the
         * target image at the position <code>(x, y, z)</code>.
         * 
         * @param x x-position of the target image.
         * @param y y-position of the target image.
         * @param z z-position of the target image.
         * @return int interpolated color at the target image position
         *               <code>(x, y, z)</code>.
         */
        public abstract int interpolate(int x, int y, int z);
    }
    
    public static final class NearestNeighborInterpolator extends Interpolator {
        
        private double[] source = new double[3];
        private double[] target = new double[3];
        
		/**
		 * @see org.wewi.medimg.image.geom.transform.ImageTransformation.Interpolator#interpolate(int, int, int)
		 */
		public int interpolate(int x, int y, int z) {
            target[0] = x;
            target[1] = y;
            target[2] = z;
            
            getTransformation().transform(target, source);
            
            int sx = (int)Math.round(source[0]);
            int sy = (int)Math.round(source[1]);
            int sz = (int)Math.round(source[2]);
            
            Dimension dim = getSourceImage().getDimension();
            if (sx > dim.getMaxX() || sx < dim.getMinX() ||
                sy > dim.getMaxY() || sy < dim.getMinY() ||
                sz > dim.getMaxZ() || sz < dim.getMinZ()) {
            
                return 0;
            }
            
			return getSourceImage().getColor(sx, sy, sz);
		}
    }


    private Transformation transformation;
    private Interpolator interpolator;

	/**
	 * Constructor for ImageTransformation.
	 */
	public ImageTransformation(Transformation transformation, Interpolator interpolator) {
		this.transformation = transformation;
        setInterpolator(interpolator);
	}
    
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        interpolator.setTransformation(transformation);
    }
    
    public Interpolator getInterpolator() {
        return interpolator;
    }

    public Image transform(Image source) {
        Image target = (Image)source.clone();
        transform(source, target);
        
        return target;
    }
    
    public void transform(Image source, Image target) {
        interpolator.setSourceImage(source);
        
        ImageLoop loop = new ImageLoop(target, new ImageLoop.Task() {
			public void execute(int x, int y, int z) {
                getImage().setColor(x, y, z, interpolator.interpolate(x, y, z));
			}
        });
        loop.loop();
    }
}








