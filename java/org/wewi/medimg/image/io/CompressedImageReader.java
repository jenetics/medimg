/*
 * CompressedImageReader.java
 *
 * Created on 18. Jänner 2002, 20:42
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class CompressedImageReader extends ImageReader {
    private ImageReader component;

    public CompressedImageReader(ImageReader component) {
        this.component = component;
    }
    
    public void read() throws ImageIOException {
        component.read();
    }
    
    File getSource() {
        return component.getSource();
    }
    
    public Image getImage() {
        return component.getImage();
    }
    
    void setSource(File source) {
        component.setSource(source);
    }
    
}
