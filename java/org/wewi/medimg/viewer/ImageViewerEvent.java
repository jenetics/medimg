/**
 * ImageViewerEvent.java
 *
 * Created on March 28, 2002, 11:41 AM
 */

package org.wewi.medimg.viewer;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewerEvent extends EventObject {
    private int slice;
    private boolean closed;
    
    /** Creates a new instance of ImageViewerEvent */
    public ImageViewerEvent(Object source, int slice, boolean closed) {
        super(source);
        this.slice = slice;
        this.closed = closed;
    }
    
    public int getSlice() {
        return slice;    
    }
    
    public boolean isClosed() {
        return closed;    
    }
}
