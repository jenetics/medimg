/**
 * RGBGreyColorConversion.java
 *
 * Created on 1. Juli 2002, 21:35
 */

package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmstötter
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
