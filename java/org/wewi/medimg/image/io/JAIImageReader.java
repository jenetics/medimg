
/**
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
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.NullImage;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
abstract class JAIImageReader extends ImageReader {
    private File[] slices = null;
    
    protected FileExtentionFilter fileFilter;
    
    public JAIImageReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);   
        init(); 
    }

    public JAIImageReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        init();
    }
    
    public JAIImageReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);  
        init();  
    }
    
    public JAIImageReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);    
        init();
    }
    
    private void init() {
        image = new NullImage();    
    }
    
    
    /**
     * Einlesen eines einzelnen Bildes als RenderedImage
     */
    private RenderedImage readRenderedImage(String filename) throws IOException {
        RenderedImage img = null;
        if (System.getProperty("JAI_IMAGE_READER_USE_CODECS") == null) {       
            img =  JAI.create("fileload", filename);
            int comp = img.getColorModel().getColorSpace().getNumComponents();
            if (comp == 3) {
                //colorConversion = new RGBColorConversion();
            }
        } else {
            SeekableStream stream = new FileSeekableStream(filename);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            img = dec.decodeAsRenderedImage();
            
            int colorType = img.getColorModel().getColorSpace().getNumComponents();
        }
        return img;
    } 
    
    /**
     * Einlesen der Bild, wenn kein Header im Verzeichnis gefunden wurde
     */
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
        //die ProgressListener werden übber den
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
            //Informieren der Listener über den Fortschritt
            count++;
            notifyProgressListener(new ImageIOProgressEvent(this, ((double)count/(double)maxSlice), false));          
        }        
    }
    
    private void readWithHeader() throws ImageIOException {
        RenderedImage rimage = null;
        Raster raster = null;
        int[] pixel = new int[3];
        Dimension dim = image.getDimension();
        System.out.println(dim);
        //Einlesen der Bilder
        int count = 0;
        int stride = range.getStride();
        for (int k = dim.getMinZ(); k <= dim.getMaxZ(); k++) {
            try {
                rimage = readRenderedImage(slices[count].toString());
                raster = rimage.getData();
            } catch (Exception e) {
                image = new NullImage();
                //Das Einlesen ist hier abgeschlossen -> Listener
                //müssen informiert werden.
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                throw new ImageIOException("Can't read JAI Image; Slice " + k, e);
            }
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
                for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) {
                    raster.getPixel(i, j, pixel);
                    //System.out.println("x: " + i + ", y: " + j + ", z: " + k);
                    image.setColor(i, j, k, colorConversion.convert(pixel));
                }
            } 
             
            //Informieren der Listener über den Fortschritt
            count++;
            notifyProgressListener(new ImageIOProgressEvent(this, 
                                        ((double)count/(double)dim.getSizeZ()), false));          
        }        
    }  
    
    private void readSingleSlice() throws ImageIOException {
        RenderedImage rimage = null;
        Raster raster = null;
        
        try {
            rimage = readRenderedImage(slices[0].toString());
            raster = rimage.getData();
            notifyProgressListener(new ImageIOProgressEvent(this, 0.5, false));
        } catch (IOException e) {
            image = new NullImage();
            //Das Einlesen ist hier abgeschlossen -> Listener
            //müssen informiert werden.
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException("Can't read JAI Image: ", e);        
        }
        
        image = imageFactory.createImage(raster.getWidth(), raster.getHeight(), 1);
        image.setColorConversion(colorConversion);         
        
        int[] pixel = new int[3];
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) {
                raster.getPixel(i, j, pixel);
                image.setColor(i, j, 0, colorConversion.convert(pixel));
            }
        }             
        
    } 
    
    /**
     * Starten des Einlesevorgangs.
     */
    public void read() throws ImageIOException {
        slices = null;
        
        
        //Wenn die Quelle kein Verzeichnis ist, wird eine einzelne Datei eingelesen.
        if (source.isFile()) {
            if (source.getName().toLowerCase().endsWith(fileFilter.getExtention())) {
                slices = new File[1];
                slices[0] = source;
                readSingleSlice(); 
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                return;       
            }
            
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
            throw new ImageIOException("The given source file must be an directory: " +
                                         source.toString());
        } else {
            slices = source.listFiles(fileFilter);
            if (slices == null) {
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                throw new ImageIOException("The given source directory contains no files: " +
                                             source.toString());
            }
            if (slices.length <= 0) {
                notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                throw new ImageIOException("The given source directory contains no files: " +
                                             source.toString());
            }            
        }
        
        
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
                    notifyProgressListener(new ImageIOProgressEvent(this, 1, true));
                    throw new ImageIOException("Can't open header", e);   
                }  
            }    
        }
        
               
        if (!image.equals(NullImage.INSTANCE)) {            
            readWithHeader();
        } else {
            readWithoutHeader(); 
        }
        
        notifyProgressListener(new ImageIOProgressEvent(this, 1, true));       
    } 
    
}
