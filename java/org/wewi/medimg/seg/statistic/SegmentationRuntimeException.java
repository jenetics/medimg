/*
 * SegmentationRuntimeException.java
 *
 * Created on 8. Mai 2002, 14:47
 */

package org.wewi.medimg.seg.statistic;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmentationRuntimeException extends RuntimeException {
    
    /**
     * Creates a new instance of <code>SegmentationRuntimeException</code> without detail message.
     */
    public SegmentationRuntimeException() {
    }
    
    
    /**
     * Constructs an instance of <code>SegmentationRuntimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SegmentationRuntimeException(String msg) {
        super(msg);
    }
    
    public SegmentationRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public SegmentationRuntimeException(Throwable cause) {
        super(cause);
    }
}
