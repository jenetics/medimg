/*
 * NullModelBasedSegmentation.java
 *
 * Created on 3. Juni 2002, 22:24
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.util.Nullable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class NullModelBasedSegmentation implements ModelBasedSegmentation,
                                                   Nullable {
    
    /** Creates a new instance of NullModelBasedSegmentation */
    public NullModelBasedSegmentation() {
    }
    
    public void segmentate(Image sourceTarget) {
    }
    
    public void segmentate(Image source, Image target) {
    }
    
    public boolean isNull() {
        return true;
    }
    
}
