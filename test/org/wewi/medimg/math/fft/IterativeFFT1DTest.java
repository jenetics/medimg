/**
 * IterativeFFT1DTest.java
 * 
 * Created on 19.12.2002, 17:19:08
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IterativeFFT1DTest extends NaiveDFT1DTest {

    /**
     * Constructor for IterativeFFT1DTest.
     * @param arg0
     */
    public IterativeFFT1DTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dft = new IterativeFFT1D();
    }
    
    public void testForwardTransformation() {
        Complex[] data = new Complex[]{new Complex(1),
                                       new Complex(2),
                                       new Complex(3),
                                       new Complex(4)};
                                       
        
        dft.transform(data);
        
        System.out.println();
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);    
        }        
        
        assertEquals(new Complex(10, 0), data[0], EPSILON);
        assertEquals(new Complex(-2, 2), data[1], EPSILON);
        assertEquals(new Complex(-2), data[2], EPSILON);
        assertEquals(new Complex(-2, -2), data[3], EPSILON);
        
        data = new Complex[]{new Complex(15),
                             new Complex(23),
                             new Complex(34),
                             new Complex(273),
                             new Complex(434),
                             new Complex(78),
                             new Complex(7),
                             new Complex(91)};
                             
        dft.transform(data);
        
        System.out.println();
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);    
        }
                             
        assertEquals(new Complex(955, 0), data[0], EPSILON);
        assertEquals(new Complex(-586.584, -116.803), data[1], EPSILON);
        assertEquals(new Complex(408, 263.), data[2], EPSILON);
        assertEquals(new Complex(-251.416, -62.8026), data[3], EPSILON);
        assertEquals(new Complex(25), data[4], EPSILON);
        assertEquals(new Complex(-251.416, + 62.8026), data[5], EPSILON); 
        assertEquals(new Complex(408, - 263.), data[6], EPSILON);
        assertEquals(new Complex(-586.584, + 116.803), data[7], EPSILON);      
             
    }

    public void testInverseTransformation() {
        Complex[] data = new Complex[]{new Complex(15),
                                       new Complex(23),
                                       new Complex(3),
                                       new Complex(43)};
                             
        dft.transformBackward(data);
                             
        System.out.println();
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);    
        }                             
                             
        assertEquals(new Complex(21, 0), data[0], EPSILON);
        assertEquals(new Complex(3, -5), data[1], EPSILON);
        assertEquals(new Complex(-12), data[2], EPSILON);
        assertEquals(new Complex(3, 5), data[3], EPSILON);        
    }    

    
    public void testBothTransformations() {
        
        
        for (int d = 0; d < 10; d++) {
            int size = (int)Math.rint(MathUtil.pow(2, d));
            
            Complex[] data = new Complex[size];
            Complex[] result = new Complex[size];
            for (int i = 0; i < data.length; i++) {
                data[i] = new Complex(Math.random()*10, Math.random()*20);
                result[i] = data[i];    
            }
            
            dft.transform(result);
            dft.transformBackward(result);
            
            for (int i = 0; i < data.length; i++) {
                assertEquals(result[i], data[i], EPSILON);    
            }
        
        }
            
    }

}