/**
 * Created on 19.11.2002 16:58:39
 *
 */
package org.wewi.medimg.image.geom;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RegularVectorFieldTest extends TestCase {
    private RegularVectorField field;

	/**
	 * Constructor for RegularVectorFieldTest.
	 * @param arg0
	 */
	public RegularVectorFieldTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        field = new RegularVectorField(new Point3D(30, 23, -23), 
                                       new int[]{20, 12, 54},
                                       new int[]{1, 3, 23});
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    

}
