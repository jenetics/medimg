/**
 * Created on 21.11.2002 14:05:24
 *
 */
package org.wewi.medimg.math.vec.ops;


/**
 * This <code>VectorFunction</code> sets all vectors of a vector field
 * to a constant value <code>constVector</code>.
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ConstVectorFunction implements VectorFunction, GridVectorFunction {
    private double[] v;

	/**
	 * Constructor for ConstVectorFunction.
	 */
	public ConstVectorFunction(double[] constVector) {
		super();
        v = new double[3];
        System.arraycopy(constVector, 0, v, 0, 3);
	}

	/**
     * Setting each vector to the value <code>constVector</code>
     * given in the constructor.
     * 
	 * @see org.wewi.medimg.math.VectorFunction#transform(double[])
	 */
	public void transform(double[] vector) {
        vector[0] = v[0];
        vector[1] = v[1];
        vector[2] = v[2];
	}
	/**
	 * @see org.wewi.medimg.math.vec.ops.GridVectorFunction#transform(int, int, int, double[])
	 */
	public void transform(int gridX, int gridY, int gridZ, double[] newVector) {
        transform(newVector);
	}

}
