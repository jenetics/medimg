package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class JPEGWriterFactory implements ImageWriterFactory {
    private Range range;
    
    public JPEGWriterFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }    


    public ImageWriter createImageWriter(Image image, File file) {
        return new JPEGWriter(image, file);
    }

    public void setRange(Range range) {
        this.range = range;
    }

}
