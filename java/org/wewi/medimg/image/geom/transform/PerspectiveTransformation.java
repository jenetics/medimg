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
public class PerspectiveTransformation extends ImageTransformation {
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transform(int[], int[])
	 */
	public void transform(int[] source, int[] target) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transform(float[], float[])
	 */
	public void transform(float[] source, float[] target) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transform(double[], double[])
	 */
	public void transform(double[] source, double[] target) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transformBackward(int[], int[])
	 */
	public void transformBackward(int[] target, int[] source) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transformBackward(float[], float[])
	 */
	public void transformBackward(float[] target, float[] source) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#transformBackward(double[], double[])
	 */
	public void transformBackward(double[] target, double[] source) {
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#scale(double)
	 */
	public Transformation scale(double alpha) {
		return null;
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#concatenate(org.wewi.medimg.image.geom.transform.Transformation)
	 */
	public Transformation concatenate(Transformation trans) {
		return null;
	}
	/**
	 * @see org.wewi.medimg.image.geom.transform.Transformation#createInverse()
	 */
	public Transformation createInverse() {
		return null;
	}



}
