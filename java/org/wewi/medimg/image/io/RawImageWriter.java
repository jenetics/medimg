/**
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
    
    private static abstract class PixelWriter {
        protected DataOutputStream out;
        
        public PixelWriter(DataOutputStream out) {
            this.out = out;    
        }
        
        public abstract void write(int p) throws IOException;
    }
    
    private static class IntegerPixelWriter extends PixelWriter {
        
        public IntegerPixelWriter(DataOutputStream out) {
            super(out);    
        }
        
        public void write(int p) throws IOException {
            out.writeInt(p);
        }   
    }
    
    private static class ShortPixelWriter extends PixelWriter {
        
        public ShortPixelWriter(DataOutputStream out) {
            super(out);    
        }
        
        public void write(int p) throws IOException {
            out.writeShort((short)p);
        }   
    } 
    
    private static class BytePixelWriter extends PixelWriter {
        
        public BytePixelWriter(DataOutputStream out) {
            super(out);    
        }
        
        public void write(int p) throws IOException {
            out.writeByte((byte)p);
        }   
    }       




    public RawImageWriter(Image image, File target) {
        super(image, target);
    }
      
    public void write() throws ImageIOException {
        FileOutputStream fout = null;
        DataOutputStream dout = null;
        ZipOutputStream zout = null;
        
        try {
            fout = new FileOutputStream(target);
            zout = new ZipOutputStream(fout);
            
            dout = new DataOutputStream(zout);
            
            zout.putNextEntry(new ZipEntry("header.xml"));
            image.getHeader().write(dout);
             
            zout.putNextEntry(new ZipEntry("image.dat"));
            int size = image.getNVoxels();
            int stride = size/100;
            for (int i = 0; i < size; i++) {
                if ((i % stride) == 0) {
                    notifyProgressListener(new ImageIOProgressEvent(this, (double)i/(double)size, false));    
                }
                dout.writeInt(image.getColor(i));    
            }
            
            dout.close();
            
        } catch (IOException ioe) {
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException(ioe);
        }
         
        notifyProgressListener(new ImageIOProgressEvent(this, 1, true));   
    } 
    
   
    
}




