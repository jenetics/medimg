/*
 * GreyRGBConversion.java
 *
 * Created on 21. Februar 2002, 12:39
 */

package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class GreyRGBConversion implements ColorConversion {
    
    public GreyRGBConversion() {
    }

    public void convert(int grey, int[] rgb) {
        rgb[0] = grey;
        rgb[1] = grey;
        rgb[2] = grey;
    }
    
    public int convert(int[] rgb) {
        return rgb[0];
    }
    
}
