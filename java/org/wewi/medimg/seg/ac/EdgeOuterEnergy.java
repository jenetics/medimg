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
 * Created on 14.11.2002 08:50:38
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.filter.ConvolutionFilter;
import org.wewi.medimg.image.filter.EdgeFilter;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.Kernel;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class EdgeOuterEnergy extends OuterEnergyFunction {
    private Image gradient;

    /**
     * Constructor for EdgeOuterEnergy.
     * @param image
     */
    public EdgeOuterEnergy(Image image) {
        super(image);
        
        gradient = (Image)image.clone();
        
        ImageFilter filter = new EdgeFilter(
                             new ConvolutionFilter(gradient, Kernel.GAUSSIAN), 
                                  Kernel.SOBEL_HORIZONTAL, Kernel.SOBEL_VERTICAL);
        filter.filter();        
    }

    /**
     * @see org.wewi.medimg.seg.ac.OuterEnergyFunction#energy(Point[])
     */
    public double energy(Point[] ac) {
        double g = 0;
        for (int i = 0, n = ac.length; i < n; i++) {
            g += gradient.getColor(ac[i].getOrdinate(0), ac[i].getOrdinate(1), 0);        
        }
        return -(double)MathUtil.sqr(g);
    }

}
