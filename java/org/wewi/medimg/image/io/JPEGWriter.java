/**
 * JPEGWriter.java
 *
 * Created on 3. October 2002, 11:59
 */


package org.wewi.medimg.image.io;

import java.io.File;
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.JPEGEncodeParam;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class JPEGWriter extends JAIImageWriter {

    public JPEGWriter(Image image, String target) {
        super(image, target);
        init();    
    }

    public JPEGWriter(Image image, File destination) {
        super(image, destination);
        init();
    }
    
    
    private void init() {
        JPEGEncodeParam param = new JPEGEncodeParam();
        encodeParameter = param;
        imageExtention = ".jpg";        
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("jpeg", out, encodeParameter);
    }

}
