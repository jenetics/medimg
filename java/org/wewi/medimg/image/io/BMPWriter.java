/**
 * BMPWriter.java
 *
 * Created on 14. Januar 2002, 11:59
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class BMPWriter extends JAIImageWriter {

    public BMPWriter(Image image, String target) {
        super(image, target);
        init();    
    }

    public BMPWriter(Image image, File destination) {
        super(image, destination);
        init();
    }
    
    
    private void init() {
        BMPEncodeParam param = new BMPEncodeParam();
        param.setVersion(BMPEncodeParam.VERSION_3);
        encodeParameter = param;
        imageExtention = ".bmp";        
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("bmp", out, encodeParameter);
    }
    
    public String toString() {
        return "BMPWriter: " + super.toString();    
    }    
    
}
