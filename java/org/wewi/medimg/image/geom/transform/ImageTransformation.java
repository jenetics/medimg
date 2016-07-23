/**
 * ImageTransformation.java
 * 
 * Created on 17.03.2003, 18:57:44
 *
 */
package org.wewi.medimg.image.geom.transform;

import java.util.logging.Logger;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.ops.ImageLoop;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageTransformation implements Transformation {
    
    public static abstract class Interpolator {
        private Transformation transformation;
        private Image sourceImage;
        
        
        private void setTransformation(Transformation transformation){
            this.transformation = transformation;
        }
        
        protected Transformation getTransformation() {
            return transformation;
        }
        
        private void setSourceImage(Image sourceImage) {
            this.sourceImage = sourceImage;
        }
        
        protected Image getSourceImage() {
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
            
            getTransformation().transformBackward(target, source);

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

    private static Logger logger = Logger.getLogger("org.wewi.medimg.image.geom.transform");

    private Interpolator interpolator;

    /**
     * Constructor for ImageTransformation.
     */
    public ImageTransformation(Interpolator interpolator) {
        setPixelInterpolator(interpolator);
    }
    
    /**
     * Constructs a ImageTransformation with the NearestNeighborInterpolator
     * as default interpolation strategy.
     * 
     * @param transformation
     */
    public ImageTransformation() {
        this(new NearestNeighborInterpolator());
    }
    
    public void setPixelInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        interpolator.setTransformation(this);
    }
    
    public Interpolator getPixelInterpolator() {
        return interpolator;
    }

    /**
     * Performes a backward transformation from a source image.
     * 
     * @param source image to be transformed.
     * @return Image transformed image.
     */
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        transform(source, target);
        
        return target;
    }
    
    /**
     * Performes a backward transformation from the source image. The
     * dimension of the target image is as large as necessary to contain
     * the whole transformed source image.
     * 
     * @param source image to be transformed.
     * @param factory image factory for the target image.
     * @return Image transformed image.
     */
    public Image transform(Image source, ImageFactory factory) {
        Dimension dim = transform(source.getDimension());
        Image target = null;
        try {
            target = factory.createImage(dim);
        } catch (OutOfMemoryError e) {
            logger.throwing(getClass().getName(), "transform(Image, Image)", e);
            target = (Image)source.clone();
        }
        
        transform(source, target);
        
        return target;
    }
    
    public void transform(Image source, Image target) {
        target.resetColor(0);
        target.setColorConversion(source.getColorConversion());
        interpolator.setSourceImage(source);
        
        ImageLoop loop = new ImageLoop(target, new ImageLoop.Task() {
            public void execute(int x, int y, int z) {
                getImage().setColor(x, y, z, interpolator.interpolate(x, y, z));
            }
        });
        loop.loop();
    }
    
    public Dimension transform(Dimension dim) {
        //Die acht Eckpunkte der Bounding-Box
        int[] p1 = {dim.getMinX(), dim.getMinY(), dim.getMinZ()};
        int[] p2 = {dim.getMaxX(), dim.getMinY(), dim.getMinZ()};
        int[] p3 = {dim.getMaxX(), dim.getMaxY(), dim.getMinZ()};
        int[] p4 = {dim.getMinX(), dim.getMaxY(), dim.getMinZ()};
        int[] p5 = {dim.getMinX(), dim.getMinY(), dim.getMaxZ()};
        int[] p6 = {dim.getMaxX(), dim.getMinY(), dim.getMaxZ()};
        int[] p7 = {dim.getMaxX(), dim.getMaxY(), dim.getMaxZ()};
        int[] p8 = {dim.getMinX(), dim.getMaxY(), dim.getMaxZ()};
        
        //Die transformierten Eckpunkte der Bounding-Box
        int[] tp1 = new int[3];
        int[] tp2 = new int[3];
        int[] tp3 = new int[3];
        int[] tp4 = new int[3];
        int[] tp5 = new int[3];
        int[] tp6 = new int[3];
        int[] tp7 = new int[3];
        int[] tp8 = new int[3];
        
        transform(p1, tp1);
        transform(p2, tp2);
        transform(p3, tp3);
        transform(p4, tp4);
        transform(p5, tp5);
        transform(p6, tp6);
        transform(p7, tp7);
        transform(p8, tp8);
        //System.out.println("DDDDDDDDDDD" + p8[0] + ", " + p8[1] + " , " + p8[2]);
        //System.out.println("DDDDDDDDDDD" + tp8[0] + ", " + tp8[1] + " , " + tp8[2]);
        //Die neuen Eckpunkte der Bounding-Box
        int minX = MathUtil.min(new int[]{tp1[0], tp2[0], tp3[0], tp4[0], tp5[0], tp6[0], tp7[0],tp8[0]});
        int minY = MathUtil.min(new int[]{tp1[1], tp2[1], tp3[1], tp4[1], tp5[1], tp6[1], tp7[1],tp8[1]});
        int minZ = MathUtil.min(new int[]{tp1[2], tp2[2], tp3[2], tp4[2], tp5[2], tp6[2], tp7[2],tp8[2]});
        int maxX = MathUtil.max(new int[]{tp1[0], tp2[0], tp3[0], tp4[0], tp5[0], tp6[0], tp7[0],tp8[0]});
        int maxY = MathUtil.max(new int[]{tp1[1], tp2[1], tp3[1], tp4[1], tp5[1], tp6[1], tp7[1],tp8[1]});
        int maxZ = MathUtil.max(new int[]{tp1[2], tp2[2], tp3[2], tp4[2], tp5[2], tp6[2], tp7[2],tp8[2]});        
      
        return new Dimension(minX, maxX, minY, maxY, minZ, maxZ);      
    }    
}








