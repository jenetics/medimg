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
 * RandomFunctionTest.java
 * 
 * Created on 12.12.2002, 12:47:15
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RandomFunctionTest extends TestCase {

    /**
     * Constructor for RandomFunctionTest.
     * @param arg0
     */
    public RandomFunctionTest(String arg0) {
        super(arg0);
    }


    public void testColorRange() {
        Image image = new IntImage(23, 12, 9);
        image.resetColor(-12);
                
        RandomFunction funct = new RandomFunction(new ColorRange(32, 456));
        UnaryPointTransformer transformer = new UnaryPointTransformer(image, funct);
        transformer.transform();
        
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        assertTrue("Minimum ist kleiner als erwartet: " + op.getMinimum(), op.getMinimum() >= 32);
        assertTrue("Maximum ist größer als erwartet: " + op.getMaximum(), op.getMaximum() <= 456);
    }
    
    

}
