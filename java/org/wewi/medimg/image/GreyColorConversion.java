/**
 * Created on 09.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
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
