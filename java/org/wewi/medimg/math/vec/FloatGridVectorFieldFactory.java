/**
 * Created on 20.11.2002 18:15:47
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class FloatGridVectorFieldFactory implements GridVectorFieldFactory {

    /**
     * Constructor for FloatGridVectorFieldFactory.
     */
    public FloatGridVectorFieldFactory() {
        super();
    }

    /**
     * @see org.wewi.medimg.math.GridVectorFieldFactory#createGridVectorField(Point3D, int[], int[])
     */
    public GridVectorField createGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
        return new FloatGridVectorField(origin, gridsXYZ, strideXYZ);
    }

}
