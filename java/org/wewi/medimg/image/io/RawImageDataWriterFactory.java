/*
 * RawImageDataWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:55
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;

import org.wewi.medimg.util.Singleton;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageDataWriterFactory implements ImageWriterFactory {
    private Range range;

    private RawImageDataWriterFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }

    public ImageWriter createImageWriter(Image image, File file) {
        ImageWriter writer = new RawImageDataWriter((ImageData)image, file);
        //writer.setRange(range);
        return writer;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
    
}
