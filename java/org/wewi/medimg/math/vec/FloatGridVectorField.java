/**
 * Created on 19.11.2002 21:50:18
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class FloatGridVectorField extends AbstractGridVectorField {

	/**
	 * Constructor for FloatGridVectorField.
	 * @param field
	 */
	public FloatGridVectorField(AbstractGridVectorField field) {
		super(field);
	}

	/**
	 * Constructor for FloatGridVectorField.
	 * @param origin
	 * @param gridsXYZ
	 * @param strideXYZ
	 */
	public FloatGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
		super(origin, gridsXYZ, strideXYZ);
	}

	/**
	 * @see org.wewi.medimg.math.AbstractGridVectorField#createRealDataArray(int, int, int)
	 */
	protected RealDataArray createRealDataArray(int sizeX, int sizeY, int sizeZ) {
		return new FloatDataArray(sizeX, sizeY, sizeZ);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone(){
		return new FloatGridVectorField(this);
	}

}
