/**
 * AllTests.java
 * 
 * Created on 20.12.2002, 15:59:24
 *
 */
package org.wewi.medimg.math.fft;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.wewi.medimg.math.fft");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(IterativeFFT1DTest.class));
        suite.addTest(new TestSuite(NaiveDFT1DTest.class));
        suite.addTest(new TestSuite(RecursiveFFT1DTest.class));
        suite.addTest(new TestSuite(DFT2DTest.class));
        suite.addTest(new TestSuite(DFT3DTest.class));
        //$JUnit-END$
        return suite;
    }
}
