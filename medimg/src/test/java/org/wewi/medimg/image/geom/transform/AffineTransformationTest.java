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
 * Created on 18.11.2002 09:29:46
 *
 */
package org.wewi.medimg.image.geom.transform;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AffineTransformationTest extends TestCase {

	/**
	 * Constructor for AffineTransformationTest.
	 * @param arg0
	 */
	public AffineTransformationTest(String arg0) {
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
    
    public void testTranlationMatrix() {
        double[]  matrix = {1, 0, 0, 123,
                             0, 1, 0, 234,
                             0, 0, 1, 12};
                             
        AffineTransformation at = new AffineTransformation(matrix);
        //System.out.println(at);
        double[] tm = at.getMatrixArray();
        
        //for (int i = 0; i < tm.length; i++) {
        //    System.out.println(tm[i]);
        //}
        
        assertEquals(123, tm[3], 0); 
        assertEquals(234, tm[7], 0);
        assertEquals(12, tm[11], 0);    
        
             
    }
    
    public void testTranslationUnMatrix() {
        double[]  matrix = {1, 0, 0, 123,
                             0, 1, 0, 234,
                             0, 0, 1, 12};
                             
        AffineTransformation at = new AffineTransformation(matrix);
        
        AffineTransformation t = at.getTranslateTransformation();
        double[] tm = t.getMatrixArray();
        
        assertEquals(123, tm[3], 0); 
        assertEquals(234, tm[7], 0);
        assertEquals(12, tm[11], 0); 
        
        
        matrix = new double[]{17, 2, 9, 45,
                               0, 5, 0, 65,
                               4, 0, 3, 54};
                             
        at = new AffineTransformation(matrix);
        //System.out.println(at);
        
        t = at.getTranslateTransformation();
        tm = t.getMatrixArray();
        
        assertEquals(45, tm[3], 0); 
        assertEquals(65, tm[7], 0);
        assertEquals(54, tm[11], 0);         
    }
    
    
    public void testRotationUnMatrix() {
        final double EPSILON = 0.00001;
        
        double[]  matrix = {1, 0, 0, 123,
                             0, 1, 0, 234,
                             0, 0, 1, 12};
                             
        AffineTransformation ar = new AffineTransformation(matrix);
        
        AffineTransformation r = ar.getRotationTransformation();
        double[] rm = r.getMatrixArray();
        
        assertEquals(1, rm[0], 0); 
        assertEquals(0, rm[1], 0);
        assertEquals(0, rm[2], 0);
        assertEquals(0, rm[4], 0);
        assertEquals(1, rm[5], 0);
        assertEquals(0, rm[6], 0);
        assertEquals(0, rm[8], 0);
        assertEquals(0, rm[9], 0);
        assertEquals(1, rm[10], 0);
        
        
        ar = AffineTransformation.getRotateInstance(3, 3*Math.PI/2, 4);
        double[] mr2 = ar.getRotationTransformation().getMatrixArray();
        double[] mr1 = ar.getMatrixArray();
        
        assertMatrix(mr1, mr2, EPSILON);
             
    }
    
    public void testShearUnMatrix() {
        final double EPSILON = 0.00001;
        
        AffineTransformation as = AffineTransformation.getShearInstance(new double[]{3,3*Math.PI/2,4});
        double[] ms2 = as.getShearTransformation().getMatrixArray();
        double[] ms1 = as.getMatrixArray();
        
//System.out.println("----------------------------------------");
//System.out.println(as);
//System.out.println(as.getShearTransformation());
//System.out.println("----------------------------------------");
        
        assertMatrix(ms1, ms2, EPSILON);        
    }
    
    private void assertMatrix(double[] m1, double[] m2, double epsilon) {
        assertEquals(m1[0], m2[0], epsilon);
        assertEquals(m1[1], m2[1], epsilon);
        assertEquals(m1[2], m2[2], epsilon);
        assertEquals(m1[3], m2[3], epsilon);
        assertEquals(m1[4], m2[4], epsilon);
        assertEquals(m1[5], m2[5], epsilon);
        assertEquals(m1[6], m2[6], epsilon);
        assertEquals(m1[7], m2[7], epsilon);
        assertEquals(m1[8], m2[8], epsilon);
        assertEquals(m1[9], m2[9], epsilon);
        assertEquals(m1[10], m2[10], epsilon);
        assertEquals(m1[11], m2[11], epsilon);        
    }
    
    public void testTransformationConcatenate() {
        
    }
    
    public void testGetRotationInstance() {
        final double EPSILON = 0.00001;
        
        AffineTransformation t = AffineTransformation.getRotateInstance(1, 2, 3);
        double[] m = t.getMatrixArray();
        
        assertEquals(0.411982, m[0], EPSILON);
        assertEquals(0.0587266, m[1], EPSILON);
        assertEquals(0.909297, m[2], EPSILON);
        assertEquals(0, m[3], EPSILON);
        assertEquals(-0.681243, m[4], EPSILON);
        assertEquals(-0.642873, m[5], EPSILON);
        assertEquals(0.350175, m[6], EPSILON);
        assertEquals(0, m[7], EPSILON);
        assertEquals(0.605127, m[8], EPSILON);
        assertEquals(-0.763718, m[9], EPSILON);
        assertEquals(-0.224845, m[10], EPSILON);
        assertEquals(0, m[11], EPSILON);   
    }

}























