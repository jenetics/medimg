/*
 * BMPReader.java
 *
 * Created on 11. Januar 2002, 14:53
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImageData;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class BMPReader extends JAIImageReader {
    
    public BMPReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        fileFilter = new FileExtentionFilter(".bmp");
    }
    
    /*
    public static void main(String[] args) {
        File file = new File("C:/cygwin/home/fwilhelm/diplom/algorithms/data/head.in.001");
        BMPReader reader = new BMPReader(ImageDataFactory.getInstance(), file);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println(data);
    }
    */    
}
