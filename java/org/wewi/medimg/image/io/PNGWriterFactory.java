package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PNGWriterFactory implements ImageWriterFactory {

    /**
     * Constructor for PNGWriterFactory.
     */
    public PNGWriterFactory() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.io.ImageWriterFactory#createImageWriter(Image, File)
     */
    public ImageWriter createImageWriter(Image image, File file) {
        ImageWriter writer = new PNGWriter(image, file);
        //writer.setRange(range);
        return writer;
    }

    /**
     * @see org.wewi.medimg.image.io.ImageWriterFactory#setRange(Range)
     */
    public void setRange(Range range) {
    }

}
