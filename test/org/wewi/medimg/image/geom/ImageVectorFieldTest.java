/**
 * Created on 15.11.2002 11:46:55
 *
 */
package org.wewi.medimg.image.geom;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageVectorFieldTest extends TestCase {
    private ImageVectorField vectorField;

	/**
	 * Constructor for ImageVectorFieldTest.
	 * @param arg0
	 */
	public ImageVectorFieldTest(String name) {
		super(name);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		vectorField = new ImageVectorField(34, 44, 12, 23, 45, 45);
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    
    public void testVectorFieldInit() {
        int[] p = new int[3];
        int[] v = new int[3];
        
        for (int i = 0, n = vectorField.getNVoxels(); i < n; i++) {
            vectorField.getCoordinates(i, p);
            vectorField.getVector(i, v);
            
            assertEquals(p[0], v[0]);
            assertEquals(p[1], v[1]);
            assertEquals(p[2], v[2]);        
        }    
    }
    
    public void testSetGetVector() {
        //vectorField.setVector(1, 40, 15, 45);
        int[] v = new int[3];
        vectorField.getVector(1, v);
        
        assertEquals(40, v[0]);
        assertEquals(15, v[1]);
        assertEquals(45, v[2]);    
    }


}
