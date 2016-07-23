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
 * Created on 14.11.2002 07:31:42
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MinMaxFunction implements UnaryFunction {
    private int min, max;

    /**
     * Constructor for MinMaxFunction.
     */
    public MinMaxFunction(int min, int max) {
        super();
        this.min = min;
        this.max = max;
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public int process(int color) {
        if (color > max) {
            return max;    
        } else if (color < min) {
            return min;    
        }
        
        return color;
    }

}
