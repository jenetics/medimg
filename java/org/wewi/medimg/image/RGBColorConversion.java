/*
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
 * Bei der Konvertierung werden die Farben auf
 * 32.000 reduziert. Das dient dazu, damit die 
 * drei Farbkomponenten noch in einem 2 Byte short
 * Datentyp Platz finden.
 */
public class RGBColorConversion implements ColorConversion {
    
    public RGBColorConversion() {
    }
    
	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int[])
	 */
    public int convert(int[] rgb) {;
    	rgb[0] = (rgb[0]/8)-1;
    	rgb[1] = (rgb[1]/8)-1;
    	rgb[2] = (rgb[2]/8)-1;
    	int r = rgb[0];
    	r *= 32;
    	r += rgb[1];
    	r *= 32;
    	r += rgb[2];
    	return r;
    }
    
	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
	 */
    public void convert(int grey, int[] rgb) {
    	int g = grey;   	
	    rgb[2] = (g%32)*8;
	    g /= 32;
	    rgb[1] = (g%32)*8;
	    g /= 32;
	    rgb[0] = (g%32)*8;
    }
    
    public Object clone() {
    	return new RGBColorConversion();	
    }
    
}
