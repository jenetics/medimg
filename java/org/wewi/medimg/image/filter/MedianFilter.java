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
 * MedianFilter.java
 * 
 * Created on 12.12.2002, 00:25:44
 *
 */
package org.wewi.medimg.image.filter;

import java.util.Arrays;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.ROIIterator;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.ops.ImageLoop;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class MedianFilter extends ImageFilter {

    /**
     * Constructor for MedianFilter.
     * @param image
     */
    public MedianFilter(Image image) {
        super(image);
    }

    /**
     * Constructor for MedianFilter.
     * @param component
     */
    public MedianFilter(ImageFilter component) {
        super(component);
    }

    protected void componentFilter() {
        Image temp = (Image)image.clone();
        temp.resetColor(0);
        
        ImageLoop loop = new ImageLoop(temp, new MedianTask());
        loop.loop();
    }
    
    private final class MedianTask extends ImageLoop.Task {
        private ROI imageROI = ROI.create(image.getDimension());
        public void execute(int x, int y, int z) {
            ROI roi = imageROI.intersect(ROI.create(x, y, z, 1));
            int[] colors = new int[roi.size()];
            
            int count = 0;
            for (VoxelIterator it = new ROIIterator(getImage(), roi); it.hasNext();) {
                colors[count++] = it.next();
            }
            
            //todo: change this with a median find algorithm (Blum?).
            Arrays.sort(colors);
            image.setColor(x, y, z, colors[colors.length/2]);
        }
        
    }

}
