/* 
 * MarginImage.java, created on 13.11.2002 15:53:02
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

package org.wewi.medimg.image;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MarginImage extends AbstractImage {
    private int slice;
    
    public MarginImage(Image image, int slice, int margin) {
        super(image.getMinX()-margin, image.getMaxX()+margin,
               image.getMinY()-margin, image.getMaxY()+margin, 
               image.getMinZ(), slice);
         
        this.slice = slice;
         
        //Kopieren des Bildinhaltes     
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <=m; j++) {
                setColor(i, j, slice, image.getColor(i, j, slice));    
            }        
        }
        
        initMargin(image, margin);        
    }
    

    public MarginImage(Image image, int margin) {
        this(image, image.getMinZ(), margin);              
    }

    private MarginImage(MarginImage id) {
        super(id);
    }
    
    protected void initMargin(Image image, int margin) {
        //"Einfärben" des oberen Randes durch Spiegeln an der Kante
        int maxY = image.getMaxY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, maxY+1+j, slice, image.getColor(i, maxY-j, slice));           
            }
        }
        
        //"Einfärben" des unteren Randes durch Spiegeln an der Kante
        int minY = image.getMinY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, minY-1-j, slice, image.getColor(i, minY+j, slice));           
            }
        }
        
        //"Einfärben" des linken Randes durch Spiegeln an der Kante
        int minX = image.getMinX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(minX-1-j, i, slice, image.getColor(minX+j, i, slice));    
            }   
        }
        
        //"Einfärben" des rechten Randes durch Spiegeln an der Kante
        int maxX = image.getMaxX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(maxX+1+j, i, slice, image.getColor(maxX-j, i, slice));    
            }   
        } 
        
        //Einfärben der Ecken
        for (int i = 0; i < margin; i++) {
            for (int j = 0; j < margin; j++) {
                setColor(minX-1-i, minY-1-j, slice, image.getColor(minX+i, minY+j, slice));
                setColor(maxX+1+i, maxY+1+j, slice, image.getColor(maxX-i, maxY-j, slice));
                setColor(maxX+1+i, minY-1-j, slice, image.getColor(maxX-i, minY+j, slice));
                setColor(minX-1-i, maxY+1+j, slice, image.getColor(minY+i, maxY-j, slice));   
            }    
        }         
    }

    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected ImageData createImageData(int size) {
        return new IntData(size);
    }
    
    public Object clone() {
    	return new MarginImage(this);
    }
    
}
