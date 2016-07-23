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
 * ImageGrid.java
 * 
 * Created on 04.03.2003, 20:19:48
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Graphics;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageGrid implements ImageCanvas {
    private int strideX;
    private int strideY;
    
    
    public ImageGrid(int strideX, int strideY) {
        this.strideX = strideX;
        this.strideY = strideY;
    }
    
    
    /**
     * @see org.wewi.medimg.viewer.image.ImageCanvas#draw(java.awt.Graphics, org.wewi.medimg.viewer.image.ImagePanel)
     */
    public void draw(Graphics graph, ImagePanel panel) {
        
    }


}
