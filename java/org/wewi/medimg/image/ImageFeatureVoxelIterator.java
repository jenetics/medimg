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
