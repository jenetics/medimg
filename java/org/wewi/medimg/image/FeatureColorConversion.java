/*
 * FeatureColorConversion.java
 *
 * Created on 21. Februar 2002, 15:55
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class FeatureColorConversion implements ColorConversion {
    private static final int[][] cc = {{255, 255, 255},
                                       {255, 0, 0},
                                       {0, 255, 0},
                                       {0, 0, 255},
                                       {255, 255, 0},
                                       {0, 255, 255},
                                       {0, 0, 0}};

    public FeatureColorConversion() {
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
