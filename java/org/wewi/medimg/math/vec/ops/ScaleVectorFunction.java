/**
 * Created on 21.11.2002 13:53:29
 *
 */
package org.wewi.medimg.math.vec.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ScaleVectorFunction implements VectorFunction {
    private double scale;

	/**
	 * Constructor for ScaleVectorFunction.
	 */
	public ScaleVectorFunction(double scale) {
		super();
        this.scale = scale;
	}


	/**
	 * @see org.wewi.medimg.math.VectorFunction#transform(double[])
	 */
	public void transform(double[] vector) {
        vector[0] *= scale;
        vector[1] *= scale;
        vector[2] *= scale;
	}

}
