package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PNGReaderFactory implements ImageReaderFactory {

    /**
     * Constructor for PNGReaderFactory.
     */
    public PNGReaderFactory() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.io.ImageReaderFactory#createImageReader(ImageFactory, File)
     */
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new PNGReader(imageFactory, file);
        return reader;
    }

    /**
     * @see org.wewi.medimg.image.io.ImageReaderFactory#setRange(Range)
     */
    public void setRange(Range range) {
    }

}
