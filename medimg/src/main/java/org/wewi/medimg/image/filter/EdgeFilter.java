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
 * Created on 14.11.2002 07:03:45
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.MarginImage;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 * 
 * @todo Codedoubeling with ConvolutionFilter!
 */
public class EdgeFilter extends ImageFilter {
    private Kernel horizontalKernel;
    private Kernel verticalKernel;

    /**
     * Constructor for EdgeFilter.
     * @param image
     */
    public EdgeFilter(Image image, Kernel horizontal, Kernel vertical) {
        super(image);
        horizontalKernel = horizontal;
        verticalKernel = vertical;
    }

    /**
     * Constructor for EdgeFilter.
     * @param component
     */
    public EdgeFilter(ImageFilter component, Kernel horizontal, Kernel vertical) {
        super(component);
        horizontalKernel = horizontal;
        verticalKernel = vertical;        
    }
    
    
    protected void componentFilter() {
        
        final int minX = image.getMinX();
        final int maxX = image.getMaxX();
        final int minY = image.getMinY();
        final int maxY = image.getMaxY();
        final int minZ = image.getMinZ();
        Image tempImage = new MarginImage(image, verticalKernel.getMargin()); 
        
        int verticalKernelSum = 0;
        int horizontalKernelSum = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                
                //Berechnen der vertikalen Maske
                verticalKernelSum = 0;
                horizontalKernelSum = 0;
                for (int k = -verticalKernel.getMargin(), n = verticalKernel.getMargin(); k <= n; k++) {
                    for (int l = -verticalKernel.getMargin(), m = verticalKernel.getMargin(); l <= m; l++) {
                        verticalKernelSum += verticalKernel.getValue(k, l) * tempImage.getColor(i+k, j+l, minZ); 
                        horizontalKernelSum += horizontalKernel.getValue(k, l) * tempImage.getColor(i+k, j+l, minZ);      
                    } 
                }
                verticalKernelSum = Math.abs((verticalKernelSum/verticalKernel.getDivisor()) + 
                                             verticalKernel.getBias());
                horizontalKernelSum = Math.abs((horizontalKernelSum/horizontalKernel.getDivisor()) + 
                                             horizontalKernel.getBias());                
                 
                image.setColor(i, j, minZ, verticalKernelSum + horizontalKernelSum);   
            }
        }                  
        
    }

}

























