/*
 * ReaderThreadEvent.java
 *
 * Created on 7. April 2002, 16:21
 */

package org.wewi.medimg.image.io;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ReaderThreadEvent extends EventObject {
    
    /** Creates a new instance of ReaderThreadEvent */
    public ReaderThreadEvent(Object source) {
        super(source);
    }
    
}
