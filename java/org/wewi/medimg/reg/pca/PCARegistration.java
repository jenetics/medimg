package org.wewi.medimg.reg.pca;

import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.reg.MultipleFeatureRegistrator;

/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public class PCARegistration extends MultipleFeatureRegistrator {

	/**
	 * Constructor for PCARegistration.
	 */
	public PCARegistration() {
		super();
	}

	/**
	 * @see org.wewi.medimg.reg.MultipleFeatureRegistrator#getTransformation(VoxelIterator, VoxelIterator)
	 */
	protected InterpolateableTransformation getTransformation(VoxelIterator source, VoxelIterator target) {
		return null;
	}

}
