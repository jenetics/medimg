/*
 * Minimizer.java
 *
 * Created on 21. Februar 2002, 09:18
 */

package org.wewi.medimg.seg.ac;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface Minimizer {
    public void minimize();
    
    public ActiveContour getActiveContour();
}

