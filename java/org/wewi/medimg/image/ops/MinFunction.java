/**
 * Created on 14.11.2002 07:34:39
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MinFunction implements UnaryFunction {
    private int min;

    /**
     * Constructor for MinFunction.
     */
    public MinFunction(int min) {
        super();
        this.min = min;
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public int process(int color) {
        if (color < min) {
            return min;    
        }
        
        return color;
    }

}
