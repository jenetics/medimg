/*
 * CompressedImageWriter.java
 *
 * Created on 14. Januar 2002, 11:22
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class CompressedImageWriter extends ImageWriter {
    private ImageWriter component;

    public CompressedImageWriter(ImageWriter component) {
        super();
        this.component = component;
    }
    
    public void write() throws ImageIOException {
        component.write();
    }
    
    File getTarget() {
        return component.getTarget();
    }
}
