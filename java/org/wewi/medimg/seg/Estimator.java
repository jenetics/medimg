/*
 * Estimator.java
 *
 * Created on 24. Juli 2002, 19:30
 */

package org.wewi.medimg.seg;

/**
 *
 * @author  Franz Wilhelmst�tter
 */
public interface Estimator {
    public void estimate();
    
    public Segmenter getSegmenter();
}
