/*
 * NullImageHeader.java
 *
 * Created on 22. Februar 2002, 09:27
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class NullImageHeader implements ImageHeader, Singleton {
    private static NullImageHeader singleton = null;
    
    
    private NullImageHeader() {
    }
    
    public static NullImageHeader getInstance() {
        if (singleton == null) {
            singleton = new NullImageHeader();
        }
        return singleton;
    }

    public void read(InputStream in) throws IOException {
    }
    
    public void write(OutputStream out) throws IOException {
    }
    
    public boolean isNull() {
        return true;
    }
    
}
