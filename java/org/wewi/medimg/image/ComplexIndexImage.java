/* 
 * ComplexIndexImage.java, created on 02.01.2003, 12:08:51
 * 
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

package org.wewi.medimg.image;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.geom.Dimension2D;
import org.wewi.medimg.math.geom.Lattice;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexIndexImage extends ImageAdapter {
    private Dimension2D dim = new Dimension2D(-100, 100, -100, 100);
    private Lattice lattice;

    /**
     * Constructor for ComplexIndexImage.
     */
    public ComplexIndexImage(ComplexImage cimage) {
        image = new IntImage(cimage.getDimension());
        init(cimage);
    }
    
    public ComplexIndexImage(ComplexImage cimage, Dimension2D complexDimension) {
        dim = complexDimension;
        image = new IntImage(cimage.getDimension());
        init(cimage);          
    }
    
    private void init(ComplexImage cimage) {
        lattice = new Lattice(dim, 20);
        
        for (int k = cimage.getMinZ(), l = cimage.getMaxZ(); k <= l; k++) {
            for (int j = cimage.getMinY(), m = cimage.getMaxY(); j <= m; j++) {
                for (int i = cimage.getMinX(), n = cimage.getMaxX(); i <= n; i++) {
                    image.setColor(i, j, k, index(cimage.getColor(i, j, k)));    
                }    
            }    
        }           
    }
    
    private int index(Complex z) {
        z = MathUtil.log(z);
        return lattice.index(z.getRe(), z.getIm());    
    }

}
