/*
 * ImageReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:32
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageReaderFactory {
    public ImageReader createImageReader(ImageFactory imageFactory, File file);
    
    public void setRange(Range range);
}

