/**
 * ConstantAffinityMetric.java
 *
 * Created on 26. März 2002, 15:09
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.Transformation;


/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public final class ConstantAffinityMetric implements AffinityMetric {

    
	/**
	 * @see org.wewi.medimg.reg.AffinityMetric#similarity(VoxelIterator, VoxelIterator, Transformation)
	 */
	public double similarity(VoxelIterator source, VoxelIterator target, Transformation trans) {
		return 1;
	}

}
