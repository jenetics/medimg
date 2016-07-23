/* 
 * ComplexPhaseImage.java, created on 17.12.2002, 13:23:14
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

import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexPhaseImage extends ImageAdapter {

    /**
     * Constructor for ComplexPhaseImage.
     */
    public ComplexPhaseImage(ComplexImage cimage) {
        super();
        
        image = new IntImage(cimage.getDimension());
        init(cimage);
    }
    
    private void init(ComplexImage cimage) {
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.setColor(i, (int)Math.log(MathUtil.argument(cimage.getColor(i)))*1000);    
        }    
    }
}
