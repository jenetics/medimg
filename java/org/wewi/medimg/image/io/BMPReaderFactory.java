/*
 * BMPReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:39
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class BMPReaderFactory implements ImageReaderFactory {
    private Range range;

    public BMPReaderFactory() {
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new BMPReader(imageFactory, file);
        reader.setRange(range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
}
