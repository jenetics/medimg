/**
 * ComplexPhaseImage.java
 * 
 * Created on 17.12.2002, 13:23:14
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexPhaseImage extends ImageAdapter {

    /**
     * Constructor for ComplexPhaseImage.
     */
    public ComplexPhaseImage(ComplexImage cimage) {
        super();
        
        image = new ImageData(cimage.getDimension());
        init(cimage);
    }
    
    private void init(ComplexImage cimage) {
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.setColor(i, (int)MathUtil.argument(cimage.getColor(i))*1000);    
        }    
    }
}
