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
