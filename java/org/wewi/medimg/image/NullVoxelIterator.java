/**
 * Created on 17.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NullVoxelIterator implements VoxelIterator {

    public NullVoxelIterator() {
    }

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new NullVoxelIterator();
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
    
    public int next(int[] p) {
        return 0;    
    }

    public int next(double[] p) {
        return 0;    
    }
    
	/**
	 * @see org.wewi.medimg.image.VoxelIterator#size()
	 */
	public int size() {
		return 0;
	}

}
