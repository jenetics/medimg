/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
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

import org.wewi.medimg.image.ByteImage;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;

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
	 * Schreiben einer einzelnen Schicht
	 */
	private void writeSliceView(int slice, Dimension dim, int color, File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		initEncoder(out);
        
		BufferedImage bufferedImage = new BufferedImage(dim.getMaxX()-dim.getMinX()+1, 
														dim.getMaxY()-dim.getMinY()+1, 
														BufferedImage.TYPE_3BYTE_BGR);
		ROI roi = ROI.create(dim).intersect(ROI.create(image.getDimension()));
		ColorConversion colorConversion = image.getColorConversion();
        
		int minX = dim.getMinX();
		int minY = dim.getMinY();
		int[] pixel = new int[3];
		int[] pixelBg = new int[3];
		colorConversion.convert(color, pixelBg);
		pixelBg[0] = 0x00FF & pixelBg[0];
		pixelBg[1] = 0x00FF & pixelBg[1];
		pixelBg[2] = 0x00FF & pixelBg[2]; 
		for (int i = dim.getMinX(), n = dim.getMaxX(); i <= n; i++) {
			for (int j = dim.getMinY(), m = dim.getMaxY(); j <= m; j++) { 
                
				if (i > roi.getMaxX() || i < roi.getMinX() ||
				j > roi.getMaxY() || j < roi.getMinY()) {
					bufferedImage.setRGB(i-minX, j-minY, (int)((0x00 << 24)|(pixelBg[0] << 16)|(pixelBg[1] << 8)|pixelBg[2]));
				} else {
					colorConversion.convert(image.getColor(i, j, slice), pixel);        
					pixel[0] = 0x00FF & pixel[0];
					pixel[1] = 0x00FF & pixel[1];
					pixel[2] = 0x00FF & pixel[2]; 
					bufferedImage.setRGB(i-minX, j-minY, (int)((0x00 << 24)|(pixel[0] << 16)|(pixel[1] << 8)|pixel[2]));
				}
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
    
	/**
	 * Schreiben des Bildes.
	 * 
	 * @throws ImageIOException wenn das Bild nicht geschrieben werden kann.
	 */
	public void writeView(Dimension dim, int color) throws ImageIOException {
		try {
            StringBuffer buffer;
            double progress = 0;
            ROI roi = ROI.create(dim).intersect(ROI.create(image.getDimension()));
            
            ///Workaround
            Image tempImage = new ByteImage(roi);
            tempImage.getHeader().setImageProperties(image.getHeader().getImageProperties());
            
			target.mkdirs();
			FileOutputStream out = new FileOutputStream(target.getPath() + File.separator + "header.xml");
			tempImage.getHeader().write(out);
			out.close();
            
            
			for (int k = roi.getMinZ(); k <= roi.getMaxZ(); k++) {
				buffer = new StringBuffer();
				buffer.append(target.getPath()).append(File.separator);
				buffer.append("image.").append(Util.format(k, 4));
				buffer.append(imageExtention);
				writeSliceView(k, dim, color, new File(buffer.toString()));
                
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
