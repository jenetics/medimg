/*
 * JAIImageReader.java
 *
 * Created on 14. Januar 2002, 10:06
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.ImageFactory;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

import java.awt.image.RenderedImage;
import java.awt.image.Raster;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.FileSeekableStream;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
abstract class JAIImageReader extends ImageReader {
    private Image image = NullImage.getInstance();
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
            return JAI.create("fileload", filename);
        } else {
            SeekableStream stream = new FileSeekableStream(filename);
            String[] names = ImageCodec.getDecoderNames(stream);
            ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
            RenderedImage im = dec.decodeAsRenderedImage();
            return im;
        }
    }    
    
    public void read() throws ImageIOException {
        File[] slices;
        if (source.isFile()) {
            if (fileFilter.accept(source)) {
                slices = new File[1];
                slices[0] = source;
            } else {
                return;
            }
        } else {
            slices = source.listFiles(fileFilter);
            if (slices == null) {
                return;
            }
            if (slices.length <= 0) {
                return;
            }            
        }
        
        //Lesen des ersten Bildes
        RenderedImage rimage = null;
        Raster raster = null;
        try {
            rimage = readRenderedImage(slices[0].toString());
            raster = rimage.getData();
            //raster = PlanarImage.wrapRenderedImage(rimage).getData();
        } catch (IOException e) {
            System.out.println("JAIImageReader.read: " + e);
            image = NullImage.getInstance();
            throw new ImageIOException("Can't read JAI Image; Slice 0");
        }
        
        int maxX = raster.getWidth();
        int maxY = raster.getHeight();
        int maxZ = slices.length;
        int[] pixel = new int[3];
        image = imageFactory.createImage(maxX, maxY, maxZ);
        
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                raster.getPixel(i, j, pixel);
                image.setColor(i, j, 0, colorConversion.convert(pixel));
                //image.setColor(i, j, 0, pixel[0]);
            }
        }

        //Einlesen der restlichen Bilder
        for (int k = 1; k < slices.length; k++) {
            try {
                rimage = readRenderedImage(slices[k].toString());
                raster = rimage.getData();
                //raster = PlanarImage.wrapRenderedImage(rimage).getData();
            } catch (Exception e) {
                System.out.println("JAIImageReader.read: " + e);
                image = NullImage.getInstance();
                throw new ImageIOException("Can't read JAI Image; Slice " + k);
            }
            for (int i = 0; i < maxX; i++) {
                for (int j = 0; j < maxY; j++) {
                    raster.getPixel(i, j, pixel);
                    image.setColor(i, j, k, colorConversion.convert(pixel));
                    //image.setColor(i, j, k, pixel[0]);
                }
            }            
        }        
    } 
    
    
    /*
    public static void main(String[] args) {
        try {
            RenderedImage image = JAIImageReader.readRenderedImage("c:/temp/in.090.tif");
            Raster raster = image.getData();
            int x = raster.getWidth();
            int y = raster.getHeight();
            int[] pixel = new int[3];
            FileOutputStream out = new FileOutputStream("c:/temp/in.o90.tif.raw");
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    raster.getPixel(i, j, pixel);
                    out.write(pixel[0]);
                    out.write(pixel[1]);
                    out.write(pixel[2]);
                }
            }
            out.close();
        } catch (Exception e) {
            System.out.println("asdf: " + e);
        }
    }
    */
}
