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
 * DFT2DTest.java
 * 
 * Created on 20.12.2002, 13:42:57
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DFT2DTest extends DFTTest {
    private final double EPSILON = 0.001;    

    /**
     * Constructor for DFT2DTest.
     * @param arg0
     */
    public DFT2DTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testWithNaiveDFT1D() {
        DFT1D dft = new NaiveDFT1D(0, 1);
        DFT2D dft2d = new DFT2D(dft);
        
        Complex[][] data = new Complex[][]{{new Complex(3), new Complex(5)},
                                            {new Complex(8), new Complex(6)},
                                            {new Complex(6), new Complex(9)}};
                                            
        dft2d.transform(data);
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.print(data[i][j]);    
            }    
            System.out.println();
        }
        System.out.println();
        
        assertEquals(new Complex(15.1052), data[0][0], EPSILON);
        assertEquals(new Complex(-1.22474), data[0][1], EPSILON);
        assertEquals(new Complex(-2.65361, -0.35353), data[1][0], EPSILON);
        assertEquals(new Complex(-0.612372, 1.76777), data[1][1], EPSILON);
        assertEquals(new Complex(-2.65361, 0.353535), data[2][0], EPSILON);
        assertEquals(new Complex(-0.612372, -1.76777), data[2][1], EPSILON);
        
        
        data = new Complex[][]{{new Complex(15.1052), new Complex(-1.22474)},
                               {new Complex(-2.65361, -0.35353), new Complex(-0.612372, 1.76777)},
                               {new Complex(-2.65361, 0.353535), new Complex(-0.612372, -1.76777)}};
        dft2d.transformInverse(data); 
        
        assertEquals(new Complex(3), data[0][0], EPSILON);
        assertEquals(new Complex(5), data[0][1], EPSILON);
        assertEquals(new Complex(8), data[1][0], EPSILON);
        assertEquals(new Complex(6), data[1][1], EPSILON);
        assertEquals(new Complex(6), data[2][0], EPSILON);
        assertEquals(new Complex(9), data[2][1], EPSILON);                                      
    }
    
    public void testWithFFT() {
        DFT1D dft = new RecursiveFFT1D(0, 1);
        DFT2D dft2d = new DFT2D(dft);
        
        int sizeX = (int)Math.rint(MathUtil.pow(2, 5));
        int sizeY = (int)Math.rint(MathUtil.pow(2, 6));
        
        Complex[][] data = new Complex[sizeX][sizeY];
        Complex[][] result = new Complex[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                data[i][j] = new Complex(Math.random()*23, Math.random()*4+3);
                result[i][j] = data[i][j];    
            }    
        }
        
        dft2d.transform(result);
        dft2d.transformInverse(result);
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                assertEquals(data[i][j], result[i][j], EPSILON);   
            }    
        }        
        
    }

}











