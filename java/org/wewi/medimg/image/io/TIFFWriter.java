/**
 * TIFFWriter.java
 *
 * Created on 14. Januar 2002, 12:09
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.TIFFEncodeParam;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class TIFFWriter extends JAIImageWriter {
    
    public TIFFWriter(Image image, String target) {
        super(image, target); 
        init();   
    }
    
    public TIFFWriter(Image image, File target) {
        super(image, target);
        init();
    }
    
    private void init() {
        TIFFEncodeParam param = new TIFFEncodeParam();
        param.setCompression(TIFFEncodeParam.COMPRESSION_PACKBITS);
        encodeParameter = param;
        imageExtention = ".tif";        
    }
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("tiff", out, encodeParameter);
    }    
    
}
