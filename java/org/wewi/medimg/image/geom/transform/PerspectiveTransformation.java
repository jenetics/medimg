/**
 * PerspectiveTransformation.java
 * 
 * Created on 17.03.2003, 22:21:37
 *
 */
package org.wewi.medimg.image.geom.transform;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PerspectiveTransformation extends AffineTransformation {

	/**
	 * Constructor for PerspectiveTransformation.
	 * @param transform
	 */
	public PerspectiveTransformation(AffineTransformation transform) {
		super(transform);
	}

	/**
	 * Constructor for PerspectiveTransformation.
	 * @param matrix
	 */
	public PerspectiveTransformation(double[] matrix) {
		super(matrix);
	}

	/**
	 * Constructor for PerspectiveTransformation.
	 * @param matrix
	 */
	public PerspectiveTransformation(double[][] matrix) {
		super(matrix);
	}

	/**
	 * @see org.wewi.medimg.image.geom.transform.InterpolateableTransformation#interpolate(org.wewi.medimg.image.geom.transform.InterpolateableTransformation, double)
	 */
	public InterpolateableTransformation interpolate(InterpolateableTransformation trans, double w) {
		return super.interpolate(trans, w);
	}

	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#concatenate(org.wewi.medimg.image.geom.transform.Transformation)
	 */
	public Transformation concatenate(Transformation trans) {
		return super.concatenate(trans);
	}

}
