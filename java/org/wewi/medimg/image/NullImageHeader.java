/*
 * NullImageHeader.java
 *
 * Created on 22. Februar 2002, 09:27
 */

package org.wewi.medimg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.wewi.medimg.util.Nullable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class NullImageHeader implements ImageHeader, Nullable {
    private static NullImageHeader singleton = null;
    
    
    public NullImageHeader() {
    }

    public void read(DataInputStream in) throws IOException {
    }
    
    public void write(DataOutputStream out) throws IOException {
    }
    
    public Dimension readDimension(DataInputStream in) throws IOException {
        return new Dimension(0, 0, 0);
    }
    
    public boolean isNull() {
        return true;
    }
    
}
