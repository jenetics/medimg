/*
 * WriterThreadListener.java
 *
 * Created on 7. April 2002, 16:06
 */

package org.wewi.medimg.image.io;

import java.util.EventListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface WriterThreadListener extends EventListener {
    public void imageWritten(WriterThreadEvent event);
}
