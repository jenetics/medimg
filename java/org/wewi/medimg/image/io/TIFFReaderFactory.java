/*
 * TIFFReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:34
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;


import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class TIFFReaderFactory implements ImageReaderFactory {
    private Range range;

    public TIFFReaderFactory() {
        range = new Range(0, Integer.MAX_VALUE, 0);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new TIFFReader(imageFactory, file);
        reader.setRange(range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
}
