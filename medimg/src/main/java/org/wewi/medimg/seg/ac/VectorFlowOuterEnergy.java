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
 * Created on 14.11.2002 09:04:25
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VectorFlowOuterEnergy extends OuterEnergyFunction {

    /**
     * Constructor for VectorFlowOuterEnergy.
     * @param image
     */
    public VectorFlowOuterEnergy(Image image) {
        super(image);
    }

    /**
     * @see org.wewi.medimg.seg.ac.OuterEnergyFunction#energy(Point[])
     */
    public double energy(Point[] ac) {
        return 0;
    }

}
