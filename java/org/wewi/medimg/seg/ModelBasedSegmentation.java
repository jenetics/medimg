/*
 * ModelBasedSegmentation.java
 *
 * Created on 8. Mai 2002, 13:40
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * 
 * @version 0.1
 */
public interface ModelBasedSegmentation {
    public void segmentate(Image source, Image target);
    
    public void segmentate(Image sourceTarget);
}
