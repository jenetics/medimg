/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

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
