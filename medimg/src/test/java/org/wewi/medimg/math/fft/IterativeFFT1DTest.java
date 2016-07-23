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
 * IterativeFFT1DTest.java
 * 
 * Created on 19.12.2002, 17:19:08
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;


/**
 * @author Franz WilhelmstÃ¶tter
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
                             
        dft.transformInverse(data);
                             
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
        dft = new IterativeFFT1D(-1, 1);
        
        for (int d = 0; d < 10; d++) {
            int size = (int)Math.rint(MathUtil.pow(2, d));
            
            Complex[] data = new Complex[size];
            Complex[] result = new Complex[size];
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

}
