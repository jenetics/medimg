/**
 * ConstantAffinityMetric.java
 *
 * Created on 26. März 2002, 15:09
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
