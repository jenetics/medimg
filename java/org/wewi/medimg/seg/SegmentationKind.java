/*
 * SegmentationKind.java
 *
 * Created on 7. April 2002, 14:28
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.util.Enumeration;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmentationKind extends Enumeration {
    public static final SegmentationKind ML = new SegmentationKind();
    public static final SegmentationKind MAP = new SegmentationKind();
    public static final SegmentationKind K_MEANS = new SegmentationKind();
    public static final SegmentationKind MCCV = new SegmentationKind();
    
    private static int refCount = 0;  
    private SegmentationKind() { 
        super(++refCount);
    }
}
