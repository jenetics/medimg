/*
 * RawImageReader.java
 *
 * Created on 25. Januar 2002, 13:32
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.NullImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.DataInputStream;

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
    
    public void read() throws ImageIOException {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(source);  
            DataInputStream in = new DataInputStream(fin);
            image = imageFactory.createImage(1, 1, 1);
            image.getHeader().read(fin);
            int size = image.getNVoxels();
            for (int i = 0; i < size; i++) {
                image.setColor(i, in.readInt());
            }
            in.close();            
        } catch (FileNotFoundException fnfe) {
            image = new NullImage();
            throw new ImageIOException("" + fnfe);
        } catch (IOException ioe) {
            image = new NullImage();
            throw new ImageIOException("" + ioe);            
        }
    }
    
    
    
    
    /*
    public static void main(String[] args) {
        RawImageReader reader = new RawImageReader(new File("C:/Temp/viewer/3dhead"), 256, 256, 109);
        reader.read();
        ImageData image = (ImageData)reader.getImage();
        
        TIFFWriter writer = new TIFFWriter(image, new File("C:/temp/3dhead"));
        writer.write();
        
    }
    */
}
