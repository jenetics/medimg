/**
 * RandomFunctionTest.java
 * 
 * Created on 12.12.2002, 12:47:15
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;

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
        Image image = new ImageData(23, 12, 9);
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
