/*
 * BMPWriter.java
 *
 * Created on 14. Januar 2002, 11:59
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.io.File;
import java.io.OutputStream;

import com.sun.media.jai.codec.ImageEncodeParam;
import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class BMPWriter extends JAIImageWriter {

    public BMPWriter(Image image, File destination) {
        super(image, destination);
        
        BMPEncodeParam param = new BMPEncodeParam();
        param.setVersion(BMPEncodeParam.VERSION_2);
        encodeParameter = param;
        imageExtention = ".bmp";
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("bmp", out, encodeParameter);
    }
    
}
