/**
 * Created on 19.11.2002 21:43:09
 *
 */
package org.wewi.medimg.math;

import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public final class DoubleGridVectorField extends AbstractGridVectorField {
    
    public DoubleGridVectorField(AbstractGridVectorField field) {
        super(field);    
    }

	/**
	 * Constructor for DoubleGridVectorField.
	 * @param origin
	 * @param gridsXYZ
	 * @param strideXYZ
	 */
	public DoubleGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
		super(origin, gridsXYZ, strideXYZ);
	}

	/**
	 * @see org.wewi.medimg.math.AbstractGridVectorField#createRealDataArray(int, int, int)
	 */
	protected RealDataArray createRealDataArray(int sizeX, int sizeY, int sizeZ) {
		return new DoubleDataArray(sizeX, sizeY, sizeZ);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new DoubleGridVectorField(this);
	}

}
