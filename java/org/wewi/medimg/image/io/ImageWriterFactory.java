/*
 * ImageWriterFactory.java
 *
 * Created on 24. Januar 2002, 10:33
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageWriterFactory {
    public ImageWriter createImageWriter(Image image, File file);
    
    public void setRange(Range range);
}

