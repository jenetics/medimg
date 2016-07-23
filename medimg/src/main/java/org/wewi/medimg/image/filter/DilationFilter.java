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
 * Created on 22.11.2002 12:24:11
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class DilationFilter extends MorphologicalOperation {

    public DilationFilter(Image image) {
        super(image);    
    }

    /**
     * Constructor for DilationFilter.
     * @param image
     * @param b
     */
    public DilationFilter(Image image, Image b) {
        super(image, b);
    }
    
    public DilationFilter(ImageFilter component) {
        super(component);    
    }

    /**
     * Constructor for DilationFilter.
     * @param component
     * @param b
     */
    public DilationFilter(ImageFilter component, Image b) {
        super(component, b);
    }

    protected int operation(int i, int j) {
        int result = 0;
        
        
        
        
        Dimension bdim = b.getDimension();
        int margin = bdim.getSizeX()/2;
        int ox = bdim.getMinX()+margin;
        int oy = bdim.getMinY()+margin;
        int minZ = image.getMinZ();

        for (int x = -margin; x <= margin; x++) {
            for (int y = -margin; y <= margin; y++) {
                result = Math.max(result, marginImage.getColor(i+x, j+y, minZ) + 
                                                    b.getColor(x+ox, y+oy, b.getMinZ()));                
            }    
        }
        
        return result;   
    }

}
