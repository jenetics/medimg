/*
 * ImageHeader.java
 *
 * Created on 22. Februar 2002, 00:12
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
public interface ImageHeader extends Nullable {
    
    public void read(DataInputStream in) throws IOException;

    public void write(DataOutputStream out) throws IOException;
    
    public Dimension readDimension(DataInputStream in) throws IOException;
    
}
