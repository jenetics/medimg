/**
 * DoubleVectorField.java
 * 
 * Created on 12.03.2003, 10:54:31
 *
 */
package org.wewi.medimg.math.vec;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleVectorField implements VectorField {
    private DoubleVectorVector doubleVector;

	/**
	 * Constructor for DoubleVectorField.
	 */
	public DoubleVectorField() {
		doubleVector = new DoubleVectorVector(10);
	}
    
	/**
	 * @see org.wewi.medimg.math.vec.VectorField#setVector(double[], double[])
	 */
	public void setVector(double[] startPoint, double[] endPoint) {
        doubleVector.add(startPoint, endPoint);
	}
    
	/**
	 * @see org.wewi.medimg.math.vec.VectorField#getVectorIterator()
	 */
	public VectorIterator getVectorIterator() {
		return doubleVector.iterator();
	}

}
