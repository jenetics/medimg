/*
 * SegmenterObserver.java
 *
 * Created on 24. Juli 2002, 20:24
 */

package org.wewi.medimg.seg;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public interface SegmenterListener {
    public void segmenterStarted(SegmenterEvent event);
    
    public void segmenterFinished(SegmenterEvent event);
}
