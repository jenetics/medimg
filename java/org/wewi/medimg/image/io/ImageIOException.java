/**
 * ImageIOException.java
 *
 * Created on 25. Februar 2002, 23:05
 */

package org.wewi.medimg.image.io;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageIOException extends Exception {

    /**
     * Creates new <code>ImageIOException</code> without detail message.
     */
    public ImageIOException() {
    }


    /**
     * Constructs an <code>ImageIOException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public ImageIOException(String msg) {
        super(msg);
    }
    
    public ImageIOException(Throwable cause) {
        super(cause);
    }
    
    public ImageIOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


