/**
 * MedianFilter.java
 * 
 * Created on 12.12.2002, 00:25:44
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MedianFilter extends ImageFilter {

    /**
     * Constructor for MedianFilter.
     * @param image
     */
    public MedianFilter(Image image) {
        super(image);
    }

    /**
     * Constructor for MedianFilter.
     * @param component
     */
    public MedianFilter(ImageFilter component) {
        super(component);
    }

    protected void componentFilter() {
        Image img = (Image)image.clone();
        
        int[] n = new int[18];
        
        for (int i = 0, v = img.getNVoxels(); i < v; i++) {
            img.getNeighbor3D18Positions(i, n);
            
            
            
            
        }
    }

}
