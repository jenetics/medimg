/* 
 * RGBAColorConversion.java, created on 24.10.2002 20:12:38
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
public class RGBAColorConversion implements ColorConversion {

    public RGBAColorConversion() {
        super();
    }

    public int convert(int[] rgba) {;
        int r = rgba[0];
        r = r << 8; //r *= 256;
        r += rgba[1];
        r = r << 8; //r *= 256;
        r += rgba[2];
        r = r << 8;
        r += rgba[3];
        return r;
    }

    public void convert(int grey, int[] rgba) {
        int g = grey;   
        rgba[3] = g & 0x00FF;
        g = g >> 8;    
        rgba[2] = g & 0x00FF;  //rgb[2] = (g%256);
        g = g >> 8;           //g /= 256;
        rgba[1] = g & 0x00FF;  //rgb[1] = (g%256);
        g = g >> 8;           //g /= 256;
        rgba[0] = g & 0x00FF;  //rgb[0] = (g%256);
    }
    
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }   
    }
    
    public String toString() {
        return getClass().getName();    
    }

}
