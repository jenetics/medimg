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
 * DirectComparison.java
 * 
 * Created on 30.12.2002, 14:26:20
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Werner Weiser
 * 
 * @since 0.1
 *
 */
public class DirectComparison {
    private AccumulatorArray accu;
    private final double N;

    /**
     * Constructor for MutualInformation.
     */
    public DirectComparison(AccumulatorArray accu) {
        if (accu == null) {
            throw new IllegalArgumentException("AccumulatorArray must not be null!");    
        }
        
        this.accu = accu;
        N = accu.elementSum();
    }
    
    /**
      */
    public double getDirectComparison(ColorRange cr1, ColorRange cr2) {
        if (N <= 0) {
            return 0;    
        }
        
        double dc = 0;
        int min = Math.max(cr1.getMinColor(), cr2.getMinColor());
        int max = Math.min(cr1.getMaxColor(), cr2.getMaxColor());
        for (int i = min; i <= max; i++) {
            dc += accu.getValue(i, i);    
        }
        return dc/N;        
    }
    

    

}