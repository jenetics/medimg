/*
 * Histogram.java
 *
 * Created on 25. Februar 2002, 13:43
 */

package org.wewi.medimg.image.statistic;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Histogram {
    private Image image;

    /** Creates new Histogram */
    public Histogram(Image image) {
        this.image = image;
    }
    
    public Image getImage() {
        return image;
    }

}
