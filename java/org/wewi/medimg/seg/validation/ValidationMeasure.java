/**
 * ValidationMeasure.java
 * 
 * Created on 07.01.2003, 20:11:22
 *
 */
package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ValidationMeasure {
    public double measure(Image modelImage, Image segmentedImage);
}
