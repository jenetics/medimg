/*
 * BMPReader.java
 *
 * Created on 11. Januar 2002, 14:53
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilhelmst�tter
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
