/*
 * JAIImageWriter.java
 *
 * Created on 14. Januar 2002, 11:20
 */

package org.wewi.medimg.image.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.ImageEncodeParam;
import com.sun.media.jai.codec.ImageEncoder;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
abstract class JAIImageWriter extends ImageWriter {
    protected ImageEncoder encoder;
    protected ImageEncodeParam encodeParameter;
    protected String imageExtention;
    
    public JAIImageWriter(Image image, File target) {
        super(image, target);
    }

    protected abstract void initEncoder(OutputStream out);
    
    private void writeSlice(int slice, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        initEncoder(out);
        
        int maxX = image.getMaxX();
        int maxY = image.getMaxY();
        BufferedImage bufferedImage = new BufferedImage(maxX+1, maxY+1, BufferedImage.TYPE_3BYTE_BGR);
        //BufferedImage bufferedImage = new BufferedImage(maxX+1, maxY+1, BufferedImage.TYPE_USHORT_GRAY);
        ColorConversion colorConversion = image.getColorConversion();
        
        int[] pixel = new int[3];
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) { 
                colorConversion.convert(image.getColor(i, j, slice), pixel);        
                // take lower 8 bits
                pixel[0] = 0x00FF & pixel[0];
                pixel[1] = 0x00FF & pixel[1];
                pixel[2] = 0x00FF & pixel[2];        
                // put this pixel in the java image
                bufferedImage.setRGB(i, j, (int)((0x00 << 24) | (pixel[0] << 16) | (pixel[1] << 8) | pixel[2]));
               // bufferedImage.setRGB(i, j, image.getColor(i, j, slice));
            }
        }
        
        encoder.encode(bufferedImage);
        out.close();
    }  
    
    private void dispose() {
        File[] files = target.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].delete()) {
                //nothing
            }
        }
        if (target.delete()) {
            //nothing
        }
    }
    
    public void write() throws ImageIOException {
        try {
            if (image.getMaxZ() == 0) {
                writeSlice(0, target);
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                return;
            } else {
                target.mkdirs();
            }

            StringBuffer buffer;
            double progress = 0;
            for (int k = image.getMinZ(); k <= image.getMaxZ(); k++) {
                buffer = new StringBuffer();
                buffer.append(target.getPath()).append(File.separator);
                buffer.append("image.").append(Util.format(k, 4));
                buffer.append(imageExtention);
                writeSlice(k, new File(buffer.toString()));
                
                progress = (double)(k-image.getMinZ())/(double)(image.getMaxZ()-image.getMinZ());
                notifyProgressListener(new ImageIOProgressEvent(this, progress, false));
            }  
        } catch (IOException ioe) {
            dispose();
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException("Can't write Image: ", ioe);
        }
        
        notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
        
    }
    
}
