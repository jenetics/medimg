/*
 * TIFFWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:44
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;


import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class TIFFWriterFactory implements ImageWriterFactory {
    private Range range;
    
    public TIFFWriterFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    

    public ImageWriter createImageWriter(Image image, File file) {
        ImageWriter writer = new TIFFWriter(image, file);
        //writer.setRange(range);
        return writer;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
}
