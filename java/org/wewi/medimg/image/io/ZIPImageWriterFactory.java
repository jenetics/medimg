/*
 * ZIPImageWriterFactory.java
 *
 * Created on 24. Januar 2002, 11:18
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ZIPImageWriterFactory extends CompressedImageWriterFactory {

    /** Creates new ZIPImageWriterFactory */
    private ZIPImageWriterFactory(ImageWriterFactory component) {
        super(component);
    }

    public static ZIPImageWriterFactory getInstance(ImageWriterFactory component) {
        return new ZIPImageWriterFactory(component);
    }
    
    public ImageWriter createImageWriter(Image image, File file) {
        return new ZIPImageWriter(super.createImageWriter(image, file));
    }
}
