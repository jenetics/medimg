/*
 * TIFFReader.java
 *
 * Created on 14. Januar 2002, 10:23
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImageData;

import java.io.File;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class TIFFReader extends JAIImageReader {

    public TIFFReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        fileFilter = new FileExtentionFilter(".tif");
    }
    
    /*
    public static void main(String[] args) {
        //TIFFReader reader = new TIFFReader("C:/cygwin/home/fwilhelm/diplom/algorithms/data/head.in.001");
        File file = new File("C:/Workspace/fwilhelm/Projekte/SRS/pic/seg/heads/001");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), file);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println(data);
    }    
    */
}
