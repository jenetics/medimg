/*
 * RawImageReader.java
 *
 * Created on 25. Januar 2002, 13:32
 */

package org.wewi.medimg.image.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.NullImage;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class RawImageReader extends ImageReader {
    private Image image;
    private int maxX, maxY, maxZ;

    public RawImageReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
    }
    
    public Image getImage() {
        return image;
    }
    
    public int getSlices() throws ImageIOException {
        return 0;
                        
    }
    
    public void read() throws ImageIOException {
        FileInputStream fin = null;
        ZipInputStream zin = null;
        DataInputStream din = null;
        try {
            fin = new FileInputStream(source);
            zin = new ZipInputStream(fin); 
            din = new DataInputStream(fin);
            zin.getNextEntry();
            
            image = imageFactory.createImage(1, 1, 1);
            image.getHeader().read(din);
            int size = image.getNVoxels();
            for (int i = 0; i < size; i++) {
                image.setColor(i, din.readInt());
            }
            din.close();            
        } catch (FileNotFoundException fnfe) {
            image = new NullImage();
            throw new ImageIOException("File not found: " + source.toString(), fnfe);
        } catch (IOException ioe) {
            image = new NullImage();
            throw new ImageIOException("Can't read Image", ioe);            
        }
    }
    
}
