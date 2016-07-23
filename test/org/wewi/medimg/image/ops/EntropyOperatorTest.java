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
 * EntropyOperatorTest.java
 * 
 * Created on 12.12.2002, 12:22:57
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class EntropyOperatorTest extends TestCase {

    /**
     * Constructor for EntropyOperatorTest.
     * @param arg0
     */
    public EntropyOperatorTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testEntropyAnalyzer() {
        Image image = new IntImage(20, 32, 21); 
        image.resetColor(32);
        
        EntropyOperator op = new EntropyOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        System.out.println("Entropy: " + op.getEntropy());
    }
    
    public void testEntropyAnalyzerRandom() {
        Image image = new IntImage(20, 32, 21); 
        image.resetColor(32);
        
        UnaryPointTransformer transformer = new UnaryPointTransformer(image, new RandomFunction());
        transformer.transform();
        
        EntropyOperator op = new EntropyOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        System.out.println("Entropy: " + op.getEntropy());
    }    

}
