/**
 * Created on 15.11.2002 19:52:04
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class TresholdFunction implements UnaryFunction {
    private int min;
    private int max;

    /**
     * Constructor for TresholdFunction.
     */
    public TresholdFunction(int min, int max) {
        super();
        this.min = min;
        this.max = max;
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public int process(int color) {
        if (color < min) {
            return 0;  
        }
        if (color > max) {
            return 0;        
        }
        
        return color;
    }

}
