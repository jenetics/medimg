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
 * Created on 22.11.2002 10:34:54
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class CannyFilter extends ImageFilter {

    /**
     * Constructor for CannyFilter.
     * @param image
     */
    public CannyFilter(Image image) {
        super(image);
    }

    /**
     * Constructor for CannyFilter.
     * @param component
     */
    public CannyFilter(ImageFilter component) {
        super(component);
    }
    
    protected void componentFilter() {
        
        Image cannyImage = (Image)image.clone();
        
        //1. 
        
        
        //TODO Implementig the Filter 
    }

}
