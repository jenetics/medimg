/**
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
//import org.wewi.medimg.math.MathUtil;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class SobelFilter extends ImageFilter {
    
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
    
    protected void componentFilter() {

        Image tempImage = (Image)image.clone();
        for (int i = image.getMinX()+1, n = image.getMaxX(); i < n; i++) {
            for (int j = image.getMinY()+1, m = image.getMaxY(); j < m; j++) {
                image.setColor(i, j, 0, enhance(i, j, tempImage));
            }
        }

        tempImage = null;        
        
    }
    
    private int enhance(int x, int y, Image img) {
        int colorX = 0;
        int colorY = 0;
        
        //Erste Zeile
        colorX += img.getColor(x-1, y+1, 0)*-1;
        colorX += img.getColor(x,   y+1, 0)*-2;
        colorX += img.getColor(x+1, y+1, 0)*-1;
        
        //Zweite Zeile
        //colorX += img.getColor(x-1, y, 0)* 0;
        //colorX += img.getColor(x,   y, 0)* 0;
        //colorX += img.getColor(x+1, y, 0)* 0;
                
        //Dritte Zeile
        colorX += img.getColor(x-1, y-1, 0)* 1;
        colorX += img.getColor(x,   y-1, 0)* 2;
        colorX += img.getColor(x+1, y-1, 0)* 1;
                        
                        
        //Erste Zeile
        colorY += img.getColor(x-1, y+1, 0)*-1;
        //colorY += img.getColor(x,   y+1, 0)*0;
        colorY += img.getColor(x+1, y+1, 0)*1;
        
        //Zweite Zeile
        colorY += img.getColor(x-1, y, 0)* -2;
        //colorY += img.getColor(x,   y, 0)* 0;
        colorY += img.getColor(x+1, y, 0)* 2;
                
        //Dritte Zeile
        colorY += img.getColor(x-1, y-1, 0)*-1;
        //colorY += img.getColor(x,   y-1, 0)*0;
        colorY += img.getColor(x+1, y-1, 0)*1;                        

        return Math.abs(colorX) + Math.abs(colorY);
        //return (int)Math.min(Math.sqrt(MathUtil.sqr(colorX)+MathUtil.sqr(colorY)), 255);
    }
    
    public String toString() {
        return getClass().getName();    
    }
    
    
    
    public static void main(String[] args) {
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), new File("c:/temp/in.090.tif"));
        try {
            reader.read();
        } catch (Exception e) {
        }
        Image image = reader.getImage();
        System.out.println(image);
        
        //ImageFilter filter = new SobelFilter(new MeanFilter(image));
        ImageFilter filter = new MeanFilter(image);
        filter.filter();
        
        TIFFWriter writer = new TIFFWriter(image, new File("C:/temp/out.img.tif"));
        try {
            writer.write();
        } catch (Exception e) {
        }
    }
}
