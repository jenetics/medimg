/*
 * ColorRange.java
 *
 * Created on 1. Juli 2002, 20:14
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ColorRange {
    private final int minColor;
    private final int maxColor;
    
    /** Creates a new instance of ColorRange */
    public ColorRange(int minColor, int maxColor) throws IllegalArgumentException {
        if (minColor > maxColor || minColor < 0) {
            throw new IllegalArgumentException("Invalid Colorrange: (" + 
                                                minColor + "," + maxColor + ")");
        }
        this.minColor = minColor;
        this.maxColor = maxColor;
    }
    
    public ColorRange(ColorRange range) {
        this(range.minColor, range.maxColor);
    }
    
    public int getMinColor() {
        return minColor;
    }
    
    public int getMaxColor() {
        return maxColor;
    }
    
    public String toString() {
        return "Colorrange: (" + minColor + "," + maxColor + ")";
    }
    
}
