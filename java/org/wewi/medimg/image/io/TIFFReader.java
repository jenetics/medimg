/*
 * TIFFReader.java
 *
 * Created on 14. Januar 2002, 10:23
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class TIFFReader extends JAIImageReader {

    public TIFFReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        fileFilter = new FileExtentionFilter(".tif");
    }
    
}
