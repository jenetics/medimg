/**
 * Created on 24.10.2002 20:12:38
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RGBAColorConversion implements ColorConversion {

	/**
	 * Constructor for RGBAColorConversion.
	 */
	public RGBAColorConversion() {
		super();
	}

    /**
     * @see org.wewi.medimg.image.ColorConversion#convert(int[])
     */
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
    
    /**
     * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
     */
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
        return new RGBAColorConversion();    
    }
    
    public String toString() {
        return getClass().getName();    
    }

}
