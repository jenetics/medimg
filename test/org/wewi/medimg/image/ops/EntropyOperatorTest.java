/**
 * EntropyOperatorTest.java
 * 
 * Created on 12.12.2002, 12:22:57
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;

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
        Image image = new ImageData(20, 32, 21); 
        image.resetColor(32);
        
        EntropyOperator op = new EntropyOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        System.out.println("Entropy: " + op.getEntropy());
    }
    
    public void testEntropyAnalyzerRandom() {
        Image image = new ImageData(20, 32, 21); 
        image.resetColor(32);
        
        UnaryPointTransformer transformer = new UnaryPointTransformer(image, new RandomFunction());
        transformer.transform();
        
        EntropyOperator op = new EntropyOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        System.out.println("Entropy: " + op.getEntropy());
    }    

}
