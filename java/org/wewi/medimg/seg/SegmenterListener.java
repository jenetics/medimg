/**
 * SegmenterObserver.java
 *
 * Created on 24. Juli 2002, 20:24
 */

package org.wewi.medimg.seg;

import java.util.EventListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface SegmenterListener extends EventListener {
    public void segmenterStarted(SegmenterEvent event);
    
    public void segmenterFinished(SegmenterEvent event);
}
