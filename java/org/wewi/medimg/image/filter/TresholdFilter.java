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
 * Created on 22.10.2002 17:22:39
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.TresholdFunction;
import org.wewi.medimg.image.ops.UnaryPointTransformer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class TresholdFilter extends ImageFilter {
    private int upperBound;
    private int lowerBound;

    /**
     * Constructor for TresholdFilter.
     * @param image
     */
    public TresholdFilter(Image image, int lowerBound, int upperBound) {
        super(image);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Constructor for TresholdFilter.
     * @param component
     */
    public TresholdFilter(ImageFilter component, int lowerBound, int upperBound) {
        super(component);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * @see org.wewi.medimg.image.filter.ImageFilter#filter()
     */
    protected void componentFilter() {
        
        UnaryPointTransformer t = new UnaryPointTransformer(image, 
                                  new TresholdFunction(lowerBound, upperBound));
        t.transform();
    }

}
