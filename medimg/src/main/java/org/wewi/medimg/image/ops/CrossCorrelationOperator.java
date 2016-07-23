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
 * CrossCorrelationOperator.java
 * 
 * Created on 23.12.2002, 14:56:12
 *
 */
package org.wewi.medimg.image.ops;


import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Werner Weiser
 * 
 * @since 0.1
 */
public class CrossCorrelationOperator implements BinaryOperator {
    private ColorRange cr1;
    private ColorRange cr2;
    
    private AccumulatorArray accu;
    /**
     * Constructor for MutualInformationOperator.
     */
    public CrossCorrelationOperator(ColorRange cr1, ColorRange cr2) {
        this.cr1 = cr1;
        this.cr2 = cr2;
        
        accu = new AccumulatorArray(cr1.getNColors(), cr2.getNColors());
    }

    /**
     * @see org.wewi.medimg.image.ops.BinaryOperator#process(int, int)
     */
    public void process(int color1, int color2) {
        accu.inc(color1 - cr1.getMinColor(), color2 - cr2.getMinColor());
    }
    
    
    public double getCrossCorrelation() {
        CrossCorrelation cc = new CrossCorrelation(accu);
        return cc.getCrossCorrelation();   
    }

}