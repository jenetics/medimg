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
 * GridLineIterator.java
 * 
 * Created on 04.03.2003, 21:27:49
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GridLineIterator implements LineIterator {
    private Point origin;
    private Dimension dim;
    private int gridsX;
    private int gridsY;

    /**
     * Constructor for GridLineIterator.
     */
    public GridLineIterator(Point origin, Dimension dim, int gridsX, int gridsY) {
        this.origin = origin;
        this.dim = dim;
        this.gridsX = gridsX;
        this.gridsY = gridsY;                         
    }

    /**
     * @see org.wewi.medimg.viewer.image.LineIterator#hasNext()
     */
    public boolean hasNext() {
        return false;
    }

    /**
     * @see org.wewi.medimg.viewer.image.LineIterator#next(int, int)
     */
    public void next(int[] begin, int[] end) {
    }

}
