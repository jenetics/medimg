package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class JPEGReaderFactory implements ImageReaderFactory {
    private Range range;

    public JPEGReaderFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new JPEGReader(imageFactory, file, range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }

}