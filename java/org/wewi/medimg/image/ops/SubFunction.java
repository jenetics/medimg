/**
 * Created on 22.11.2002 14:47:40
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class SubFunction implements BinaryFunction {

    /**
     * Constructor for SubFunction.
     */
    public SubFunction() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.ops.BinaryFunction#process(int, int)
     */
    public int process(int value1, int value2) {
        return value1 - value2;
    }

}
