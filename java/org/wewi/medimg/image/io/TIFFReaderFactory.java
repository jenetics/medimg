/*
 * TIFFReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:34
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
public final class TIFFReaderFactory implements ImageReaderFactory, Singleton {
    private static TIFFReaderFactory singleton = null;

    private TIFFReaderFactory() {
    }
    
    public static TIFFReaderFactory getInstance() {
        if (singleton == null) {
            singleton = new TIFFReaderFactory();
        }
        return singleton;
    }

    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return new TIFFReader(imageFactory, file);
    }
    
}
