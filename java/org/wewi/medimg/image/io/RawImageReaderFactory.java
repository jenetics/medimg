/*
 * RawImageDataReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:47
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;

import org.wewi.medimg.util.Singleton;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageReaderFactory implements ImageReaderFactory, Singleton {
    private static RawImageReaderFactory singleton = null;

    private RawImageReaderFactory() {
    }

    public static RawImageReaderFactory getInstance() {
        if (singleton == null) {
            singleton = new RawImageReaderFactory();
        }
        return singleton;
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return new RawImageReader(imageFactory, file);
    }
    
}
