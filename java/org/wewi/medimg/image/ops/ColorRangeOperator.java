/* 
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

/**
 * Created on 14.11.2002 06:02:01
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ColorRangeOperator implements UnaryOperator {
    private boolean firstCall = true;
    
    private int min, max;
    private int calls = 0;

    /**
     * Constructor for ColorRangeOperator.
     */
    public ColorRangeOperator() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public void process(int color) {
        ++calls;
        if (firstCall) {
            min = max = color; 
            firstCall = false;   
        }
        
        if (min > color) {
            min = color;    
        } else if (max < color) {
            max = color;    
        }
    }
    
    public int getMinimum() {
        return min;    
    }
    
    public int getMaximum() {
        return max;    
    }
    
    public ColorRange getColorRange() {
        return new ColorRange(min, max);    
    }
    
    public int getCalls() {
        return calls;    
    }
    
    public String toString() {
        return "MinColor: " + min + ", MaxColor: " + max;    
    }

}
