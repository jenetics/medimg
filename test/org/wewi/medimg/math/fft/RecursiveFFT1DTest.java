/**
 * RecursiveFFT1DTest.java
 * 
 * Created on 19.12.2002, 16:22:33
 *
 */
package org.wewi.medimg.math.fft;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RecursiveFFT1DTest extends IterativeFFT1DTest {

    /**
     * Constructor for RecursiveFFT1DTest.
     * @param arg0
     */
    public RecursiveFFT1DTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dft = new RecursiveFFT1D();
    } 
    
    //public void testInverseTransformation() {
    //}
    
    //public void testBothTransformations() {
    //}   
    
}
