/*
 * RawImageDataWriter.java
 *
 * Created on 18. Jänner 2002, 13:55
 */

package org.wewi.medimg.image.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.wewi.medimg.image.Image;

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
        DataOutputStream dout = null;
        FileOutputStream fout = null;
        ZipOutputStream zout = null;
        int size = image.getNVoxels();
        
        try {
            fout = new FileOutputStream(target);
            zout = new ZipOutputStream(fout);
            zout.putNextEntry(new ZipEntry("image.rid"));
            dout = new DataOutputStream(fout);
            image.getHeader().write(dout);
            for (int i = 0; i < size; i++) {
                dout.writeInt(image.getColor(i));
            }
            dout.close();
        } catch (IOException ioe) {
            target.delete();
            throw new ImageIOException("Can't write Image", ioe);
        }
    }

}




