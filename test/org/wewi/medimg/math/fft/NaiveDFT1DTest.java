/**
 * NaiveDFT1DTest.java
 * 
 * Created on 19.12.2002, 15:27:45
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class NaiveDFT1DTest extends DFTTest {
    protected static final double EPSILON = 0.01;

    /**
     * Constructor for NaiveDFT1DTest.
     * @param arg0
     */
    public NaiveDFT1DTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dft = new NaiveDFT1D();
    }
    
    public void testForwardTransformation() {
        Complex[] data = new Complex[]{new Complex(1),
                                       new Complex(2),
                                       new Complex(3),
                                       new Complex(4),
                                       new Complex(5)};
                                       
        
        dft.transform(data);
        
        assertEquals(new Complex(15, 0), data[0], EPSILON);
        assertEquals(new Complex(-2.5, 3.44095), data[1], EPSILON);
        assertEquals(new Complex(-2.5, 0.812299), data[2], EPSILON);
        assertEquals(new Complex(-2.5, -0.812299), data[3], EPSILON);
        assertEquals(new Complex(-2.5, -3.44095), data[4], EPSILON);
        
        data = new Complex[]{new Complex(15),
                             new Complex(23),
                             new Complex(3.54),
                             new Complex(43),
                             new Complex(5.4),
                             new Complex(3.45)};
                             
        dft.transform(data);
                             
        assertEquals(new Complex(93.39, 0), data[0], EPSILON);
        assertEquals(new Complex(-19.245, -15.32), data[1], EPSILON);
        assertEquals(new Complex(40.305, -18.5416), data[2], EPSILON);
        assertEquals(new Complex(-45.51, 0), data[3], EPSILON);
        assertEquals(new Complex(40.305, 18.5416), data[4], EPSILON);
        assertEquals(new Complex(-19.245, 15.32), data[5], EPSILON);       
              
    }
    
    public void testInverseTransformation() {
        Complex[] data = new Complex[]{new Complex(15),
                                       new Complex(23),
                                       new Complex(3.54),
                                       new Complex(43),
                                       new Complex(5.4),
                                       new Complex(3.45)};
                             
        dft.transformInverse(data);
                             
        assertEquals(new Complex(15.565, 0), data[0], EPSILON);
        assertEquals(new Complex(-3.2075, 2.55333), data[1], EPSILON);
        assertEquals(new Complex(6.7175, 3.09027), data[2], EPSILON);
        assertEquals(new Complex(-7.585, 0), data[3], EPSILON);
        assertEquals(new Complex(6.7175, -3.09027), data[4], EPSILON);
        assertEquals(new Complex(-3.2075, -2.55333), data[5], EPSILON);        
    }
    
    public void testBothTransformations() {
        dft = new NaiveDFT1D(-1.5, 1);
        
        Complex[] data = new Complex[64];
        Complex[] result = new Complex[64];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Complex(Math.random()*10, Math.random()*20);
            result[i] = data[i];    
        }
        
        dft.transform(result);
        dft.transformInverse(result);
        
        for (int i = 0; i < data.length; i++) {
            assertEquals(result[i], data[i], EPSILON);    
        }    
    }

}
