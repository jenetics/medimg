/*
 * ImageViewerListener.java
 *
 * Created on March 28, 2002, 11:40 AM
 */

package org.wewi.medimg.viewer;

import java.util.EventListener;

//import javax.swing.event.InternalFrameListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageViewerListener extends EventListener {
    public void update(ImageViewerEvent event);
}
