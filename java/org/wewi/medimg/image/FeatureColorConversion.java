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
    private static final Color[] c = {Color.white,
                                          Color.red,
                                          Color.green,
                                          Color.blue,
                                          Color.orange,
                                          Color.pink,
                                          Color.yellow,
                                          Color.cyan,
                                          Color.lightGray,
                                          Color.gray,
                                          Color.darkGray,
                                          Color.black};
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
        rgb[0] = cc[grey][0];
        rgb[1] = cc[grey][1];
        rgb[2] = cc[grey][2];
    }
    
    public int convert(int[] rgb) {
        return 1;
    }
    
}
