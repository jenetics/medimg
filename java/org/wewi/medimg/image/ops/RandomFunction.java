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
 * RandomFunction.java
 * 
 * Created on 12.12.2002, 12:30:52
 *
 */
package org.wewi.medimg.image.ops;

import java.util.Random;

import org.wewi.medimg.image.ColorRange;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RandomFunction implements UnaryFunction {
    private ColorRange range;
    private Random rand;

    /**
     * Constructor for RandomFunction.
     */
    public RandomFunction() {
        this(new ColorRange(0, 255));
    }
    
    public RandomFunction(ColorRange range) {
        this.range = range;
        rand = new Random(System.currentTimeMillis());    
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public int process(int color) {
        int c = (int)((double)range.getNColors()*rand.nextDouble() + (double)range.getMinColor());
        return c;
    }

}
