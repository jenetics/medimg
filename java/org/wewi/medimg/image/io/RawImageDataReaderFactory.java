/*
 * RawImageDataReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:47
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageDataReaderFactory implements ImageReaderFactory {
    private Range range;

    public RawImageDataReaderFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new RawImageDataReader(file);
        reader.setRange(range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
    
}
