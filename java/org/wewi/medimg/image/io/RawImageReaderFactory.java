/*
 * RawImageDataReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:47
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageReaderFactory implements ImageReaderFactory, Singleton {
    private static RawImageReaderFactory singleton = null;
    private Range range;

    public RawImageReaderFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new RawImageReader(imageFactory, file);
        reader.setRange(range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
    
}
