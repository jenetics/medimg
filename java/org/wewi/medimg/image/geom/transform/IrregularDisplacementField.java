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


	public IrregularDisplacementField() {
		field = new DoubleVectorField();
	}

	public Transformation scale(double alpha) {
		return null;
	}

	public Transformation concatenate(Transformation trans) {
		return null;
	}

	public Transformation createInverse() {
		return null;
	}

	public void addVector(double[] startPoint, double[] endPoint) {
        field.addVector(startPoint, endPoint);
	}

	public VectorIterator getVectorIterator() {
		return field.getVectorIterator();
	}


}
