/*
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
    
    /** Creates a new instance of ImageViewerEvent */
    public ImageViewerEvent(Object source) {
        super(source);
    }
}
