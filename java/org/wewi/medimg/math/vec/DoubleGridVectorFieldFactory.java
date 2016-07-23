/**
 * Created on 20.11.2002 18:14:49
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleGridVectorFieldFactory implements GridVectorFieldFactory {

    /**
     * Constructor for DoubleGridVectorFieldFactory.
     */
    public DoubleGridVectorFieldFactory() {
        super();
    }

    /**
     * @see org.wewi.medimg.math.GridVectorFieldFactory#createGridVectorField(Point3D, int[], int[])
     */
    public GridVectorField createGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
        return new DoubleGridVectorField(origin, gridsXYZ, strideXYZ);
    }

}
