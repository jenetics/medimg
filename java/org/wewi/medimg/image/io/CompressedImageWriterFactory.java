/*
 * CompressedImageWriterFactory.java
 *
 * Created on 24. Januar 2002, 11:15
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class CompressedImageWriterFactory implements ImageWriterFactory {
    private ImageWriterFactory component;

    protected CompressedImageWriterFactory(ImageWriterFactory component) {
        this.component = component;
    }

    public ImageWriter createImageWriter(Image image, File file) {
        return component.createImageWriter(image, file);
    }
    
}
