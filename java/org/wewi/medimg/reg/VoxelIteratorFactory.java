/**
 * Created on 24.09.2002
 *
 */
package org.wewi.medimg.reg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
final class VoxelIteratorFactory {
    private Image source;
    private Image target;

	/**
	 * Constructor for VoxelIteratorFactory.
	 */
	public VoxelIteratorFactory(Image source, Image target) {
		this.source = source;
        this.target = target;
	}
    
    public boolean haseJointVoxelIterator(int feature) {
        return true;    
    }
    
    public VoxelIterator getSourceVoxelIterator(int feature) {
        return null;    
    }
    
    public VoxelIterator getTargetVoxelIterator(int feature) {
        return null;    
    }

}
