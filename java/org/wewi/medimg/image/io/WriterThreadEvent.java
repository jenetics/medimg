/*
 * WriterThreadEvent.java
 *
 * Created on 7. April 2002, 16:23
 */

package org.wewi.medimg.image.io;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class WriterThreadEvent extends EventObject {
    
    /** Creates a new instance of WriterThreadEvent */
    public WriterThreadEvent(Object source) {
        super(source);
    }
    
}
