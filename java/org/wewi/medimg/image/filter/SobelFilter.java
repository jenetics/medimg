/*
 * SobelFilter.java
 *
 * Created on 21. Februar 2002, 17:11
 */

package org.wewi.medimg.image.filter;

import java.io.File;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class SobelFilter extends ImageFilter {
    public Image gradImage;
    
    private class Mask {
        //public short get
    }

    /** Creates new SobelFilter */
    public SobelFilter(Image image) {
        super(image);
    }
    
    public SobelFilter(ImageFilter component) {
        super(component);
    }
    
    public void filter() {
        gradImage = (Image)image.clone();
        int maxX = image.getMaxX();
        int maxY = image.getMaxY();
        for (int i = 1; i < maxX-1; i++) {
            for (int j = 1; j < maxY-1; j++) {
                gradImage.setColor(i, j, 0, enhance(i, j, image));
            }
        }
        
        super.filter();
    }
    
    public Image getImage() {
        return gradImage;    
    }
    
    
    private short enhance(int x, int y, Image image) {
        double colorX = 0;
        double colorY = 0;
        colorX += image.getColor(x-1, y+1, 0)*-1;
        colorX += image.getColor(x,   y+1, 0)*-2;
        colorX += image.getColor(x+1, y+1, 0)*-1;
        colorX += image.getColor(x+1, y,   0)* 0;
        colorX += image.getColor(x+1, y-1, 0)* 1;
        colorX += image.getColor(x,   y-1, 0)* 2;
        colorX += image.getColor(x-1, y-1, 0)* 1;
        colorX += image.getColor(x-1, y,   0)* 0;

        colorY += image.getColor(x-1, y+1, 0)*-1;
        colorY += image.getColor(x,   y+1, 0)* 0;
        colorY += image.getColor(x+1, y+1, 0)* 1;
        colorY += image.getColor(x+1, y,   0)* 2;
        colorY += image.getColor(x+1, y-1, 0)* 1;
        colorY += image.getColor(x,   y-1, 0)* 0;
        colorY += image.getColor(x-1, y-1, 0)*-1;
        colorY += image.getColor(x-1, y,   0)*-2;

        
        return (short)((colorX/8.0)+(colorY/8.0));
    }
    
    
    
    public static void main(String[] args) {
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), new File("c:/temp/in.090.tif"));
        try {
            reader.read();
        } catch (Exception e) {
        }
        Image image = reader.getImage();
        System.out.println(image);
        
        //ImageFilter filter = new SobelFilter(new BlurFilter(image));
        ImageFilter filter = new BlurFilter(image);
        filter.filter();
        
        TIFFWriter writer = new TIFFWriter(image, new File("C:/temp/out.img.tif"));
        try {
            writer.write();
        } catch (Exception e) {
        }
    }
}
