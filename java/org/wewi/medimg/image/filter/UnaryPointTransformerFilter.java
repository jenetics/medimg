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
 * Created on 14.11.2002 07:44:28
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.UnaryPointTransformer;
import org.wewi.medimg.image.ops.UnaryPointTransformerFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnaryPointTransformerFilter extends ImageFilter {
    private UnaryPointTransformerFactory factory;

    /**
     * Constructor for UnaryPointTransformerFilter.
     * @param image
     */
    public UnaryPointTransformerFilter(Image image, UnaryPointTransformerFactory factory) {
        super(image);
        this.factory = factory;
    }

    /**
     * Constructor for UnaryPointTransformerFilter.
     * @param component
     */
    public UnaryPointTransformerFilter(ImageFilter component, UnaryPointTransformerFactory factory) {
        super(component);
        this.factory = factory;
    }
    
    protected void componentFilter() {   
        UnaryPointTransformer t = factory.createTransformer(image);
        t.transform();
    }

}
