/**
 * IrregularDisplacementField.java
 * 
 * Created on 12.03.2003, 10:58:32
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.math.vec.DoubleVectorField;
import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IrregularDisplacementField extends DisplacementF {
    private VectorField field;

	/**
	 * Constructor for IrregularDisplacementField.
	 */
	public IrregularDisplacementField() {
		field = new DoubleVectorField();
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

	/**
	 * @see org.wewi.medimg.math.vec.VectorField#setVector(double, double)
	 */
	public void setVector(double[] startPoint, double[] endPoint) {
        field.setVector(startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.VectorField#getVectorIterator()
	 */
	public VectorIterator getVectorIterator() {
		return field.getVectorIterator();
	}

}
