/*
 * TIFFWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:44
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
public final class TIFFWriterFactory implements ImageWriterFactory, Singleton {
    private static TIFFWriterFactory singleton = null;

    private TIFFWriterFactory() {
    }
    
    public static TIFFWriterFactory getInstance() {
        if (singleton == null) {
            singleton = new TIFFWriterFactory();
        }
        return singleton; 
    }

    public ImageWriter createImageWriter(Image image, File file) {
        return new TIFFWriter(image, file);
    }
    
}
