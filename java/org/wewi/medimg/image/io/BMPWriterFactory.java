/*
 * BMPWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:50
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.util.Singleton;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class BMPWriterFactory implements ImageWriterFactory, Singleton {
    private static BMPWriterFactory singleton = null;

    private BMPWriterFactory() {
    }
    
    public static BMPWriterFactory getInstance() {
        if (singleton == null) {
            singleton = new BMPWriterFactory();
        }
        return singleton;
    }

    public ImageWriter createImageWriter(Image image, File file) {
        return new BMPWriter(image, file);
    }
    
}
