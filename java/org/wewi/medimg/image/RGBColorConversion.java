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
public class RGBColorConversion implements ColorConversion {
    
    public RGBColorConversion() {
    }
    
	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int[])
	 */
    public int convert(int[] rgb) {;
    	int r = rgb[0];
    	r *= 256;
    	r += rgb[1];
    	r *= 256;
    	r += rgb[2];
    	return r;
    }
    
	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
	 */
    public void convert(int grey, int[] rgb) {
    	int g = grey;   	
	    rgb[2] = (g%256);
	    g /= 256;
	    rgb[1] = (g%256);
	    g /= 256;
	    rgb[0] = (g%32);
    }
    
    public Object clone() {
    	return new RGBColorConversion();	
    }
    
    public String toString() {
        return getClass().getName();    
    }
    
}
