/*
 * JAIImageReader.java
 *
 * Created on 14. Januar 2002, 10:06
 */

package org.wewi.medimg.image.io;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.media.jai.JAI;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.NullImage;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.2
 */
abstract class JAIImageReader extends ImageReader {
    private Image image = new NullImage();
    private File[] slices = null;
    
    protected FileExtentionFilter fileFilter;

    public JAIImageReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        this.imageFactory = imageFactory;
        
        this.source = source;
    }
    
    public Image getImage() {
        return image;
    }
    
    
    private RenderedImage readRenderedImage(String filename) throws IOException {
        if (System.getProperty("JAI_IMAGE_READER_USE_CODECS") == null) {       
            RenderedImage img =  JAI.create("fileload", filename);
            int comp = img.getColorModel().getColorSpace().getNumComponents();
            if (comp == 3) {
                //colorConversion = new RGBColorConversion();
            }
            return img;
        } else {
            SeekableStream stream = new FileSeekableStream(filename);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            
            int colorType = im.getColorModel().getColorSpace().getNumComponents();
            System.out.println("Comp: " + colorType);
            
            return im;
        }
    } 
    
    private void readWithoutHeader() throws ImageIOException {
        RenderedImage rimage = null;
        Raster raster = null;         
        
        int minSlice = 0;
        int maxSlice = slices.length-1;
        if (range.getMinSlice() > maxSlice) {
            image = new NullImage();
            return;
        }
        minSlice = range.getMinSlice();
        maxSlice = Math.min(range.getMaxSlice(), maxSlice); 
        
        //Lesen des ersten Bildes
        try {
            rimage = readRenderedImage(slices[minSlice].toString());
            raster = rimage.getData();
        } catch (IOException e) {
            image = new NullImage();
            throw new ImageIOException("Can't read JAI Image; Slice 0", e);
        }
    
        //Lesen des ersten Bildes ist beendet und
        //die ProgressListener werden �bber den
        //Fortschritt informiert.
        notifyProgressListener(new ImageIOProgressEvent(this, (1.0/(double)maxSlice), false));
        
        int sizeX = raster.getWidth();
        int sizeY = raster.getHeight();
        int sizeZ = maxSlice - minSlice + 1;
        int[] pixel = new int[3];
        
        //Erzeugen des Bildes, wenn alle Daten bekannt sind
        image = imageFactory.createImage(sizeX, sizeY, sizeZ);
        image.setColorConversion((ColorConversion)colorConversion.clone());
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                raster.getPixel(i, j, pixel);
                image.setColor(i, j, 0, colorConversion.convert(pixel));
            }
        }

        //Einlesen der restlichen Bilder
        int count = 1;
        int stride = range.getStride();
        for (int k = minSlice+1; k <= maxSlice; k += stride) {
            try {
                rimage = readRenderedImage(slices[k].toString());
                raster = rimage.getData();
            } catch (Exception e) {
                image = new NullImage();
                throw new ImageIOException("Can't read JAI Image; Slice " + k, e);
            }
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    raster.getPixel(i, j, pixel);
                    image.setColor(i, j, k-minSlice, colorConversion.convert(pixel));
                }
            }  
            //Informieren der Listener �ber den Fortschritt
            count++;
            notifyProgressListener(new ImageIOProgressEvent(this, ((double)count/(double)maxSlice), false));          
        }        
    }   
    
    public void read() throws ImageIOException {
        slices = null;
        
        //Wenn die Quelle kein Verzeichnis ist, ist das Einlesen beendet.
        if (source.isFile()) {
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            return;
        } else {
            slices = source.listFiles(fileFilter);
            if (slices == null) {
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                return;
            }
            if (slices.length <= 0) {
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                return;
            }            
        }
        
        //ASSERT: Ab hier mu� das Array der Schichten gef�llt sein.
        assert(slices == null);
        
        //Einlesen des Headers, falls vorhanden
        File[] fileList = source.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].toString().endsWith("header.xml")) {
                try {
                    FileInputStream in = new FileInputStream(fileList[i]);
                    image = imageFactory.createImage(1, 1, 1);
                    image.getHeader().read(in);
                    colorConversion = image.getColorConversion();
                    break;  
                } catch (Exception e) {
                    System.err.println(getClass().getName() + ":" + e);
                    //nichts    
                }  
            }    
        }
        
               
        if (!image.equals(NullImage.IMAGE_INSTANCE)) {
            System.out.println("JAIImageReader: Bild mit Header");
            
            
            RenderedImage rimage = null;
            Raster raster = null;
            int[] pixel = new int[3];
            Dimension dim = image.getDimension();
            //Einlesen der Bilder
            int count = 0;
            int stride = range.getStride();
            for (int k = dim.getMinZ(); k <= dim.getMaxZ(); k++) {
                try {
                    rimage = readRenderedImage(slices[count].toString());
                    raster = rimage.getData();
                } catch (Exception e) {
                    image = new NullImage();
                    throw new ImageIOException("Can't read JAI Image; Slice " + k, e);
                }
                for (int i = 0; i < dim.getSizeX(); i++) {
                    for (int j = 0; j < dim.getSizeY(); j++) {
                        raster.getPixel(i, j, pixel);
                        image.setColor(i, j, k, colorConversion.convert(pixel));
                    }
                }  
                //Informieren der Listener �ber den Fortschritt
                count++;
                notifyProgressListener(new ImageIOProgressEvent(this, ((double)count/(double)dim.getSizeZ()), false));          
            }        
        } else {
            System.out.println("JAIImageReader: Bild ohne Header");
            readWithoutHeader(); 
        }
        
        notifyProgressListener(new ImageIOProgressEvent(this, 1, true));       
    } 
    
}
