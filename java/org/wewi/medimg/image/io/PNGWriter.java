package org.wewi.medimg.image.io;

import java.io.File;
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.PNGEncodeParam;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PNGWriter extends JAIImageWriter {

    /**
     * Constructor for PNGWriter.
     * @param image
     * @param target
     */
    public PNGWriter(Image image, File target) {
        super(image, target);
        init();
    }

    /**
     * Constructor for PNGWriter.
     * @param image
     * @param target
     */
    public PNGWriter(Image image, String target) {
        super(image, target);
        init();
    }

    private void init() {
        PNGEncodeParam param = new PNGEncodeParam.RGB();
        encodeParameter = param;
        imageExtention = ".png";        
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("png", out, encodeParameter);
    }
    
    public String toString() {
        return "PNGWriter: " + super.toString();    
    } 

}
