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
    
    public JAIImageWriter(Image image, String target) {
        super(image, target);    
    }
    
    /**
     * Schreiben einer einzelnen Schicht
     */
    private void writeSlice(int slice, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        initEncoder(out);
        
        BufferedImage bufferedImage = new BufferedImage(image.getMaxX()-image.getMinX()+1, 
                                                        image.getMaxY()-image.getMinY()+1, 
                                                        BufferedImage.TYPE_3BYTE_BGR);
        ColorConversion colorConversion = image.getColorConversion();
        
        int minX = image.getMinX();
        int minY = image.getMinY();
        int[] pixel = new int[3];
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) { 
                colorConversion.convert(image.getColor(i, j, slice), pixel);        

                pixel[0] = 0x00FF & pixel[0];
                pixel[1] = 0x00FF & pixel[1];
                pixel[2] = 0x00FF & pixel[2];        
                bufferedImage.setRGB(i-minX, j-minY, (int)((0x00 << 24)|(pixel[0] << 16)|(pixel[1] << 8)|pixel[2]));
            }
        }
        
        encoder.encode(bufferedImage);
        out.close();
    } 
    
    /**
     * Die initialisierung der JAI-Parameter muß von
     * den abgeleiteten Klassen durchgeführt werden.
     */
    protected abstract void initEncoder(OutputStream out);     
    
    /**
     * Wenn beim Schreiben etwas schief geht, müssen
     * die bereits geschriebenen Datein gelöscht werden.
     */
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
    
    /**
     * Schreiben des Bildes.
     * 
     * @throws ImageIOException wenn das Bild nicht geschrieben werden kann.
     */
    public void write() throws ImageIOException {
        try {
            target.mkdirs();
            FileOutputStream out = new FileOutputStream(target.getPath() + File.separator + "header.xml");
            image.getHeader().write(out);
            out.close();

            StringBuffer buffer;
            double progress = 0;
            for (int k = image.getMinZ(); k <= image.getMaxZ(); k++) {
                buffer = new StringBuffer();
                buffer.append(target.getPath()).append(File.separator);
                buffer.append("image.").append(Util.format(k, 4));
                buffer.append(imageExtention);
                writeSlice(k, new File(buffer.toString()));
                
                //Informieren der ProgressListener
                progress = (double)(k-image.getMinZ())/(double)(image.getMaxZ()-image.getMinZ());
                notifyProgressListener(new ImageIOProgressEvent(this, progress, false));
            }  
        } catch (IOException ioe) {
            dispose();
            throw new ImageIOException("Can't write Image: ", ioe);
        } finally {
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));    
        }
    }
    
}
