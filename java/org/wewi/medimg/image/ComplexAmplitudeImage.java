/**
 * ComplexAmplitudeImage.java
 * 
 * Created on 17.12.2002, 13:04:18
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexAmplitudeImage extends ImageAdapter {

    /**
     * Constructor for ComplexAmplitudeImage.
     */
    public ComplexAmplitudeImage(ComplexImage cimage) {
        super();
        
        image = new IntImage(cimage.getDimension());
        init(cimage);
    }
    
    private void init(ComplexImage cimage) {
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.setColor(i, (int)Math.log((MathUtil.abs(cimage.getColor(i))+1)*1000));    
        }    
    }

}
