/**
 * Created on 24.09.2002
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 * 
 */
public class ImageFeatureVoxelIterator implements VoxelIterator {

    /**
     * Constructor for ImageFeatureVoxelIterator.
     */
    public ImageFeatureVoxelIterator() {
        super();
    }

    /**
     * @see org.wewi.medimg.image.VoxelIterator#hasNext()
     */
    public boolean hasNext() {
        return false;
    }

    /**
     * @see org.wewi.medimg.image.VoxelIterator#next()
     */
    public int next() {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.VoxelIterator#next(int[])
     */
    public int next(int[] p) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.VoxelIterator#next(double[])
     */
    public int next(double[] p) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.VoxelIterator#size()
     */
    public int size() throws UnsupportedOperationException {
        return 0;
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return null;
    }

}
