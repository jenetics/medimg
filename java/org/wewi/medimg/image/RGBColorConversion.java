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
 * RGBGreyColorConversion.java
 *
 * Created on 1. Juli 2002, 21:35
 */

package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 * 
 */
public final class RGBColorConversion implements ColorConversion {
    
    public RGBColorConversion() {
    }
    
    /**
     * @see org.wewi.medimg.image.ColorConversion#convert(int[])
     */
    public int convert(int[] rgb) {;
        int r = rgb[0];
        r = r << 8; //r *= 256;
        r += rgb[1];
        r = r << 8; //r *= 256;
        r += rgb[2];
        return r;
    }
    
    /**
     * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
     */
    public void convert(int grey, int[] rgb) {
        int g = grey;       
        rgb[2] = g & 0x00FF;  //rgb[2] = (g%256);
        g = g >> 8;           //g /= 256;
        rgb[1] = g & 0x00FF;  //rgb[1] = (g%256);
        g = g >> 8;           //g /= 256;
        rgb[0] = g & 0x00FF;  //rgb[0] = (g%256);
    }
    
    public Object clone() {
        return new RGBColorConversion();    
    }
    
    public String toString() {
        return getClass().getName();    
    }
    
}
