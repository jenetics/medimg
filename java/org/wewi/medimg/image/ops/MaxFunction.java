/**
 * Created on 14.11.2002 07:33:36
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MaxFunction implements UnaryFunction {
    private int max;

	/**
	 * Constructor for MaxFunction.
	 */
	public MaxFunction(int max) {
		super();
        this.max = max;
	}

	/**
	 * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
	 */
	public int process(int color) {
        if (color > max) {
            return max;    
        }
		return color;
	}

}
