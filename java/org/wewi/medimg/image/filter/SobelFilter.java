/* 
 * SobelFilter.java, created on 21. Februar 2002, 17:11
 * 
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


package org.wewi.medimg.image.filter;

import java.io.File;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;


/**
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class SobelFilter extends ImageFilter {
    
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
        TIFFReader reader = new TIFFReader(IntImageFactory.getInstance(), new File("c:/temp/in.090.tif"));
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
