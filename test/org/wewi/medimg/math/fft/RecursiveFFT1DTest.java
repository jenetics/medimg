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
