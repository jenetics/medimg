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
