/*
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
    
    public TIFFWriter(Image image, File destination) {
        super(image, destination);
        
        TIFFEncodeParam param = new TIFFEncodeParam();
        encodeParameter = param;
        imageExtention = ".tif";
    }
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("tiff", out, encodeParameter);
    }    
    
    /*
    public static void main(String[] args) {
        File source = new File("C:/cygwin/home/fwilhelm/diplom/algorithms/data/head.in.001");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), source);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println(data);
     
        ZIPImageWriter writer = new ZIPImageWriter(new TIFFWriter(data, new File("c:/temp/out")));
        writer.write();
     
    }
     */
}
