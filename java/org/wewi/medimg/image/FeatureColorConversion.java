/*
 * FeatureColorConversion.java
 *
 * Created on 21. Februar 2002, 15:55
 */

package org.wewi.medimg.image;

import java.awt.Color;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class FeatureColorConversion implements ColorConversion {
    private static final Color[] c = {Color.WHITE,
                                        Color.RED,
                                        Color.GREEN,
                                        Color.BLUE,
                                        Color.BLACK,
                                        Color.PINK,
                                        Color.YELLOW,
                                        Color.CYAN,
                                        Color.LIGHT_GRAY,
                                        Color.GRAY,
                                        Color.DARK_GRAY,
                                        Color.ORANGE};
    private static final int colors = c.length;
    private static int[][] cc;

    public FeatureColorConversion() {
        cc = new int[colors][3];
        for (int i = 0; i < colors; i++) {
            cc[i][0] = c[i].getRed();
            cc[i][1] = c[i].getGreen();
            cc[i][2] = c[i].getBlue();
        }
    }

    public void convert(int grey, int[] rgb) {
        rgb[0] = cc[grey%colors][0];
        rgb[1] = cc[grey%colors][1];
        rgb[2] = cc[grey%colors][2];
    }
    
    public int convert(int[] rgb) {
        return 1;
    }
    
    public Object clone() {
    	return new FeatureColorConversion();	
    }
    
}
