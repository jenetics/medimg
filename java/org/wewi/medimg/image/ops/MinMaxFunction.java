/**
 * Created on 14.11.2002 07:31:42
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MinMaxFunction implements UnaryFunction {
    private int min, max;

	/**
	 * Constructor for MinMaxFunction.
	 */
	public MinMaxFunction(int min, int max) {
		super();
        this.min = min;
        this.max = max;
	}

	/**
	 * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
	 */
	public int process(int color) {
        if (color > max) {
            return max;    
        } else if (color < min) {
            return min;    
        }
        
        return color;
	}

}
