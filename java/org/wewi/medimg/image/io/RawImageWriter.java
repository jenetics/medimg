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
    
}




