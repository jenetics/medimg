/*
 * BMPReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:39
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
public final class BMPReaderFactory implements ImageReaderFactory, Singleton {
    private static BMPReaderFactory singleton = null;

    private BMPReaderFactory() {
    }
    
    public static BMPReaderFactory getInstance() {
        if (singleton == null) {
            singleton = new BMPReaderFactory();
        }
        return singleton;
    }

    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return new BMPReader(imageFactory, file);
    }
    
}
