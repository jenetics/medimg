/*
 * RawImageDataWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:55
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class RawImageWriterFactory implements ImageWriterFactory {
    private Range range;

    public RawImageWriterFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    

    public ImageWriter createImageWriter(Image image, File file) {
        ImageWriter writer = new RawImageWriter(image, file);
        //writer.setRange(range);
        return writer;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
}
