/**
 * DFTTest.java
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
public class DFTTest extends TestCase {
    protected DFT1D dft;

    /**
     * Constructor for DFTTest.
     * @param arg0
     */
    public DFTTest(String arg0) {
        super(arg0);
    }
    
    public void assertEquals(Complex a, Complex b, double EPSILON) {
        assertEquals(a.getRe(), b.getRe(), EPSILON);
        assertEquals(a.getIm(), b.getIm(), EPSILON);    
    }

}
