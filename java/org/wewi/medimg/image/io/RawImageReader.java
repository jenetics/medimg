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
            din = new DataInputStream(zin);
            
            image = imageFactory.createImage(1, 1, 1);
            zin.getNextEntry();
            image.getHeader().read(din);
            din.close();
            
            //Der JDOM SAXInputter schließt leider den Stream nach dem Einleisen
            fin = new FileInputStream(source);
            zin = new ZipInputStream(fin); 
            din = new DataInputStream(zin);            
            
            zin.getNextEntry();
            zin.getNextEntry();
            int size = image.getNVoxels();
            int stride = size/100;
            for (int i = 0; i < size; i++) {
                if ((i % stride) == 0) {
                    notifyProgressListener(new ImageIOProgressEvent(this, (double)i/(double)size, false));    
                }
                image.setColor(i, din.readInt());
            }
            din.close();            
        } catch (FileNotFoundException fnfe) {
            image = new NullImage();
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException("File not found: " + source.toString(), fnfe);
        } catch (IOException ioe) {
            image = new NullImage();
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException("Can't read Image: " + ioe, ioe);            
        }
        
        notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
    }
    
}
