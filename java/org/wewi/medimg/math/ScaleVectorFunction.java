/**
 * Created on 21.11.2002 13:53:29
 *
 */
package org.wewi.medimg.math;

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
        vector[0] *= vector[0];
        vector[1] *= vector[1];
        vector[2] *= vector[2];
	}

}
