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
 * Created on 13.11.2002 09:59:21
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.MarginImage;
import org.wewi.medimg.image.ops.ImageLoop;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ConvolutionFilter extends ImageFilter {
    protected Kernel kernel;

    /**
     * Constructor for ConvolutionFilter.
     * @param image
     */
    public ConvolutionFilter(Image image, Kernel kernel) {
        super(image);
        this.kernel = kernel;
    }

    /**
     * Constructor for ConvolutionFilter.
     * @param component
     */
    public ConvolutionFilter(ImageFilter component, Kernel kernel) {
        super(component);
        this.kernel = kernel;
    }
    
    protected void componentFilter() {
        
        final int minX = image.getMinX();
        final int maxX = image.getMaxX();
        final int minY = image.getMinY();
        final int maxY = image.getMaxY();
        final int minZ = image.getMinZ();
        Image tempImage = new MarginImage(image, kernel.getMargin());
        KernelMaskSum maskSum = new KernelMaskSum(tempImage, minZ, kernel);
        

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {                
                image.setColor(i, j, minZ, (int)maskSum.getKernelMaskSum(i, j));  
            }
        }   
    }
    
    /**
     * Implements the diskrete 3D convolution.
     */
    private void convolution() {
        ImageLoop loop;
    }
    
    private final class ConvolutionTask extends ImageLoop.Task {
        private Image sourceImage;
        
        public ConvolutionTask(Image sourceImage) {
            this.sourceImage = sourceImage;
        }
        
        /**
         * @see org.wewi.medimg.image.ops.ImageLoop.Task#execute(int, int, int)
         */
        public void execute(int x, int y, int z) {
        }
    }
}




















