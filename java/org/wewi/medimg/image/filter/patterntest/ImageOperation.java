/**
 * Created on 23.10.2002 21:48:57
 *
 */
package org.wewi.medimg.image.filter.patterntest;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageOperation {
    public void operate(Image source, Image target);
}
