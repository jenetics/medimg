/**
 * ImageLoop.java
 *
 * Created on 23. Jänner 2003, 18:20
 */

package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageLoop {
    
    public static abstract class Task {
        /**
         * The <code>image</code> variable is set by the
         * <code>ImageLoop</code> class. You don't have
         * to do this in this class. 
         */
        private Image image;
        
        private void setImage(Image image) {
            this.image = image;
        }
        
        /**
         * Getting a reference of the image, the <code>ImageLoop</code>
         * walk through.
         *
         * @return reference of the <code>ImageLoop</code>-image.
         */
        public Image getImage() {
            return image;
        }
        
        public abstract void execute(int x, int y, int z);
    }
    
    
    
    protected Image image;
    private Task task;
    
    /** Creates a new instance of ImageLoop */
    public ImageLoop(Image image, Task task) {
        this.image = image;
        this.task = task;
        
        task.setImage(image);
    }
    
    protected ImageLoop() {
    }
    
    /**
     * Loops the whole image.
     */
    public void loop() {
        loop(ROI.create(image.getDimension()));
    }
    
    /**
     * Loops the part of the image, specified by the region of interest <code>roi</code>
     *
     * @param roi region of interest for the loop.
     */
    public void loop(ROI roi) {
        loop(roi, 1);
    }
    
    /**
     * Loops the whole image with the specified strides.
     *
     * @param strideX stride in x-direction.
     * @param strideY stride in y-direction.
     * @param strideZ stride in z-direction.
     */
    public void loop(int strideX, int strideY, int strideZ) {
        loop(ROI.create(image.getDimension()), strideX, strideY, strideZ);
    }
    
    /**
     * Loops the whole image with the specified stride (in all directions).
     *
     * @param stride stride in all direction.
     */    
    public void loop(int stride) {
        loop(ROI.create(image.getDimension()), stride);
    }
    
    /**
     * Loops the part of the image, specified by the region of interest <code>roi</code> and
     * the stride <code>stride</code> in all directions
     *
     * @param roi region of interest for the loop.
     * @param stride stride in all direction.
     */    
    public void loop(ROI roi, int stride) {
        loop(roi, stride, stride, stride);
    }
    
    /**
     * The <code>loop</code> is executed as follows:
     *  
     * <pre>
     *     for (int k = roi.getMinZ(), u = roi.getMaxZ(); k <= u; k += strideZ) {
     *         for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j += strideY) {
     *             for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i += strideX) {
     *                 task.execute(i, j, k);
     *             }
     *         }
     *     }
     * </pre>
     *
     * @param roi Region of interest. The part of the image that should be looped.
     * @param strideX stride in x-direction
     * @param strideY stride in y-direction
     * @param strideZ stride in z-direction
     *
     */
    public void loop(ROI roi, int strideX, int strideY, int strideZ) {
        for (int k = roi.getMinZ(), u = roi.getMaxZ(); k <= u; k += strideZ) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j += strideY) {
                for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i += strideX) {
                    task.execute(i, j, k);
                }
            }
        }        
    }
    
    public String toString() {
        return "Loop over the image:\n" + image.toString();
    }
    
}
