/**
 * Created on 22.11.2002 14:48:18
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AddFunction implements BinaryFunction {

	/**
	 * Constructor for AddFunction.
	 */
	public AddFunction() {
		super();
	}

	/**
	 * @see org.wewi.medimg.image.ops.BinaryFunction#process(int, int)
	 */
	public int process(int value1, int value2) {
		return value1 + value2;
	}

}
