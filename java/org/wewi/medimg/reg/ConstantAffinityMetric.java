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
 * ConstantAffinityMetric.java
 *
 * Created on 26. M�rz 2002, 15:09
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.transform.Transformation;


/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public final class ConstantAffinityMetric implements AffinityMetric {
    
    public static final ConstantAffinityMetric INSTANCE = new ConstantAffinityMetric();
    
    public ConstantAffinityMetric() {
    }

    
	/**
	 * @see org.wewi.medimg.reg.AffinityMetric#similarity(VoxelIterator, VoxelIterator, Transformation)
	 */
	public double similarity(Image source, Image target, Transformation trans) {
		return 1;
	}
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof ConstantAffinityMetric)) {
            return false;    
        }
        return true;
    }
    
    
    public String toString() {
        return "ConstantAffinityMetric";   
    }

}
