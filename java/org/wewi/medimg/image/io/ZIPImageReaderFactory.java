/*
 * ZIPImageReaderFactory.java
 *
 * Created on 24. Januar 2002, 11:08
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class ZIPImageReaderFactory extends CompressedImageReaderFactory {
                                                                                          
    private ZIPImageReaderFactory(ImageReaderFactory component) {
        super(component);
    }
    
    public static ZIPImageReaderFactory getInstance(ImageReaderFactory component) {
        return new ZIPImageReaderFactory(component);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        return new ZIPImageReader(super.createImageReader(imageFactory, file));
    }
}
