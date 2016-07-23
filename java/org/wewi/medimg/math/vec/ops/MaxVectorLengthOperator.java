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
 * Created on 21.11.2002 13:50:00
 *
 */
package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MaxVectorLengthOperator implements VectorOperator {
    private double maxLength = 0;

    /**
     * Constructor for MaxVectorLengthOperator.
     */
    public MaxVectorLengthOperator() {
        super();
    }

    /**
     * @see org.wewi.medimg.math.VectorOperator#process(double[], double[])
     */
    public void process(double[] start, double[] end) {
        double length = MathUtil.sqr(end[0] - start[0]) + 
                         MathUtil.sqr(end[1] - start[1]) +
                         MathUtil.sqr(end[2] - start[2]);
        if (maxLength < length) {
            maxLength = length;    
        }
    }
    
    public double getMaxLength() {
        return Math.sqrt(maxLength);    
    }

}
