/*
 * Segmenter.java
 *
 * Created on 24. Juli 2002, 19:46
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public interface Segmenter {
    public void segment(Image mrt, Image segimg);
    
    public Image segment(Image mrt);
}
