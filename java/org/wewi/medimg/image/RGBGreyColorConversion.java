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
 */
public class RGBGreyColorConversion implements ColorConversion {
    private final int colors;
    private final double mult;
    
    /** Creates a new instance of RGBGreyColorConversion */
    public RGBGreyColorConversion(int colors) {
        this.colors = colors;
        mult = (double)colors/(double)16777216d; //colors/256^3
    }
    
    public int convert(int[] rgb) {
        int sum = rgb[0] + rgb[1] + rgb[2];
        return (int)Math.round(sum/3d);
    }
    
    public void convert(int grey, int[] rgb) {
        rgb[0] = (int)(grey/mult);
        rgb[1] = (int)(grey/mult);
        rgb[2] = (int)(grey/mult);
    }
    
}
