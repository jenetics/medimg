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
 * Created on 20.11.2002 18:31:41
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class KernelMaskSum {
    private Image image;
    private Kernel kernel;
    private int slice;
    

    /**
     * Constructor for KernelMaskSum.
     */
    public KernelMaskSum(Image image, int slice, Kernel kernel) {
        super();
        this.image = image;
        this.kernel = kernel;
        this.slice = slice;
    }
    
    public double getKernelMaskSum(int i, int j) {
        double kernelSum = 0;
        for (int k = -kernel.getMargin(), n = kernel.getMargin(); k <= n; k++) {
            for (int l = -kernel.getMargin(), m = kernel.getMargin(); l <= m; l++) {
                kernelSum += kernel.getValue(k, l) * image.getColor(i+k, j+l, slice);       
            } 
        } 
           
        return kernelSum/(double)kernel.getDivisor();    
    }

}
