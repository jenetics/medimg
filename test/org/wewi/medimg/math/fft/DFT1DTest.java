/**
 * DFT1DTest.java
 * 
 * Created on 19.12.2002, 12:47:33
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DFT1DTest extends TestCase {
    protected DFT1D dft;

    /**
     * Constructor for DFT1DTest.
     * @param arg0
     */
    public DFT1DTest(String arg0) {
        super(arg0);
    }
    
    public void assertEquals(Complex a, Complex b, double EPSILON) {
        assertEquals(a.real(), b.real(), EPSILON);
        assertEquals(a.imag(), b.imag(), EPSILON);    
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

}
