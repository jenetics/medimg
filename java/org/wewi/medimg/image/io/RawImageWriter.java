/*
 * RawImageDataWriter.java
 *
 * Created on 18. Jänner 2002, 13:55
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class RawImageWriter extends ImageWriter {

    public RawImageWriter(Image image, File target) {
        super(image, target);
    }
    
    public void write() throws ImageIOException {
        DataOutputStream out = null;
        FileOutputStream fout = null;
        int size = image.getNVoxels();
        
        try {
            fout = new FileOutputStream(target);
            out = new DataOutputStream(fout);
            image.getHeader().write(fout);
            for (int i = 0; i < size; i++) {
                out.writeInt(image.getColor(i));
            }
            out.close();
        } catch (IOException ioe) {
            target.delete();
            throw new ImageIOException("Can't write Image", ioe);
        }
    }
    
    /*
    public static void main(String[] args) {
        File source = new File("C:/Workspace/fwilhelm/Projects/SRS/data/srsSEG/segTest/head.in.001");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), source);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println(data);
        
        ImageWriter writer = new RawImageDataWriter(data, new File("c:/temp/out.rid"));
        ZIPImageWriter zipWriter = new ZIPImageWriter(writer);
        zipWriter.write();
    }
    */
}




