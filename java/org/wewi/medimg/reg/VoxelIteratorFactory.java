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
    
    public boolean hasJointVoxelIterator(int feature) {

	    VoxelIterator sit = new FeatureIterator(source, feature);    
	    VoxelIterator tit = new FeatureIterator(target, feature);    
	    
	    if (!(sit.hasNext()) || !(tit.hasNext())) {
	    	return false;    
	    }
        return true;    
    }
    
    public VoxelIterator getSourceVoxelIterator(int feature) {
        return new FeatureIterator(source, feature);    
    }
    
    public VoxelIterator getTargetVoxelIterator(int feature) {
        return new FeatureIterator(target, feature);    
    }

}
