/*
 * JAIImageReader.java
 *
 * Created on 14. Januar 2002, 10:06
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.RGBGreyColorConversion;

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
    private Image image = new NullImage();
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
            RenderedImage im =  JAI.create("fileload", filename);
            int comp = im.getColorModel().getColorSpace().getNumComponents();
            if (comp == 3) {
                colorConversion = new RGBGreyColorConversion(65000);
            }
            return im;
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
        
        //Wenn ein Bereich festgelegt wurde, wird dies hier berücksichtigt
        int minSlice = 0;
        int maxSlice = slices.length-1;
        if (range.getMinSlice() > maxSlice) {
            image = new NullImage();
            return;
        }
        minSlice = range.getMinSlice();
        maxSlice = Math.min(range.getMaxSlice(), maxSlice);
        //System.out.println("JAIImageReader(88): min: " + minSlice + ",  max: " + maxSlice); 
        
        //Lesen des ersten Bildes
        RenderedImage rimage = null;
        Raster raster = null;
        try {
            rimage = readRenderedImage(slices[minSlice].toString());
            raster = rimage.getData();
        } catch (IOException e) {
            image = new NullImage();
            throw new ImageIOException("Can't read JAI Image; Slice 0", e);
        }
        
        int sizeX = raster.getWidth();
        int sizeY = raster.getHeight();
        int sizeZ = maxSlice - minSlice + 1;
        int[] pixel = new int[3];
        image = imageFactory.createImage(sizeX, sizeY, sizeZ);
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                raster.getPixel(i, j, pixel);
                image.setColor(i, j, 0, colorConversion.convert(pixel));
            }
        }

        //Einlesen der restlichen Bilder
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
