/*
 * ReaderThreadListener.java
 *
 * Created on 7. April 2002, 15:58
 */

package org.wewi.medimg.image.io;

import java.util.EventListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ReaderThreadListener extends EventListener {
    public void imageRead(ReaderThreadEvent event);
}
