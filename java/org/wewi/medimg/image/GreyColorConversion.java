/**
 * Created on 09.08.2002
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GreyColorConversion implements ColorConversion {
	
	public GreyColorConversion() {
	}

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
	 */
	public void convert(int grey, int[] rgb) {
        rgb[0] = grey;
        rgb[1] = grey;
        rgb[2] = grey;		
	}

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int[])
	 */
	public int convert(int[] rgb) {
        //return (rgb[0]+rgb[1]+rgb[2])/3;
        return rgb[0];
	}

	public Object clone() {
		return new GreyColorConversion();	
	}
}
