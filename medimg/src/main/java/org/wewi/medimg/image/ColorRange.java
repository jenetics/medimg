/* 
 * ColorRange.java, created on 1. Juli 2002, 20:14
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


package org.wewi.medimg.image;

import org.wewi.medimg.util.Immutable;

/**
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ColorRange implements Immutable {
    private final int minColor;
    private final int maxColor;
    private final int nColors;
    
    /** Creates a new instance of ColorRange */
    public ColorRange(int minColor, int maxColor) throws IllegalArgumentException {
        if (minColor > maxColor || minColor < 0) {
            throw new IllegalArgumentException("Invalid Colorrange: (" + 
                                                minColor + "," + maxColor + ")");
        }
        this.minColor = minColor;
        this.maxColor = maxColor;
        nColors = maxColor-minColor+1;
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
    
    public int getNColors() {
        return nColors;    
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + minColor;
        result = 37*result + maxColor;
        return result;    
    }
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColorRange)) {
            return false;
        }            
        
        ColorRange cr = (ColorRange)obj;
        return cr.minColor == minColor && cr.maxColor == maxColor;
    }
    
    public String toString() {
        return "Colorrange: (" + minColor + "," + maxColor + ")";
    }
    
}
