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
public final class RawImageDataReaderFactory implements ImageReaderFactory, Singleton {
    private static RawImageDataReaderFactory singleton = null;

    private RawImageDataReaderFactory() {
    }

    public static RawImageDataReaderFactory getInstance() {
        if (singleton == null) {
            singleton = new RawImageDataReaderFactory();
        }
        return singleton;
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return new RawImageDataReader(file);
    }
    
}
