/*
 * RawImageDataWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:55
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;

import org.wewi.medimg.util.Singleton;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageDataWriterFactory implements ImageWriterFactory, Singleton {
    private static RawImageDataWriterFactory singleton = null;

    private RawImageDataWriterFactory() {
    }
    
    public static RawImageDataWriterFactory getInstance() {
        if (singleton == null) {
            singleton = new RawImageDataWriterFactory();
        }
        return singleton;
    }

    public ImageWriter createImageWriter(Image image, File file) {
        return new RawImageDataWriter((ImageData)image, file);
    }
    
}
