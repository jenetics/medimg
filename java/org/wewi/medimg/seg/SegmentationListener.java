/*
 * SegmentationListener.java
 *
 * Created on 7. April 2002, 15:45
 */

package org.wewi.medimg.seg;

import java.util.EventListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface SegmentationListener extends EventListener {
    public void segmentationStarted(SegmentationEvent event);
    
    public void segmentationFinished(SegmentationEvent event);
    
    public void iterationFinished(SegmentationEvent event);
}
