/*
 * Histogram.java
 *
 * Created on 28. Juni 2002, 19:33
 */

package org.wewi.medimg.seg.statistic;

import java.util.Arrays;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Histogram {
    private Image image;
    private double[] frequency;
    
    /** Creates a new instance of Histogram */
    public Histogram(Image image) {
        this.image = image;
        frequency = new double[256];
        Arrays.fill(frequency, 0);
    }
    
    
    public void generate() {
        int size = image.getNVoxels();
        int c;
        for (int i = 0; i < size; i++) {
            c = image.getColor(i);
            frequency[c]++;
        }
        for (int i = 0; i < 256; i++) {
            frequency[i] /= size;
        }
    }
    
    public double getRelativeFrequency(int color) {
        return frequency[color];
    }
}
