/*
 * ImageReaderFactory.java
 *
 * Created on 24. Januar 2002, 10:32
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public interface ImageReaderFactory {
    public ImageReader createImageReader(ImageFactory imageFactory, File file);
    
    public void setRange(Range range);
}

