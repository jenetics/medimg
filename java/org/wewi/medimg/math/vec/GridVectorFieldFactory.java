/**
 * Created on 20.11.2002 18:07:08
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface GridVectorFieldFactory {
    public GridVectorField createGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ);
}
