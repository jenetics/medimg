/*
 * Segmentation.java
 *
 * Created on 17. Januar 2002, 15:34
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.FeatureData;

import java.util.Observable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ImageSegmentationStrategy extends Observable implements SegmentationStrategy {
    protected Image image;
    protected FeatureData featureData;
    
    public ImageSegmentationStrategy(Image image) {
        this.image = image;
    }
    
    public FeatureData getFeatureData() {
        return featureData;
    }
    
    public Image getImage() {
        return image;
    }
}

