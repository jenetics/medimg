/*
 * CompressedImageReaderFactory.java
 *
 * Created on 24. Januar 2002, 11:03
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class CompressedImageReaderFactory implements ImageReaderFactory {
    private ImageReaderFactory component;
    private Range range;

    protected CompressedImageReaderFactory(ImageReaderFactory component) {
        this.component = component;
        range = new Range(0, Integer.MAX_VALUE, 1);
    }

    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = component.createImageReader(imageFactory, file);
        reader.setRange(range);
        return component.createImageReader(imageFactory, file);
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
    
}
