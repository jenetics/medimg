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

    protected CompressedImageReaderFactory(ImageReaderFactory component) {
        this.component = component;
    }

    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return component.createImageReader(imageFactory, file);
    }
}
