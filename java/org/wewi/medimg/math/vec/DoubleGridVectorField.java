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
 * Created on 19.11.2002 21:43:09
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;

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
