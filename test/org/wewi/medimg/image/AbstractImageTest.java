/**
 * Created on 08.11.2002 12:27:09
 *
 */
package org.wewi.medimg.image;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class AbstractImageTest extends TestCase {
    protected Image image;

	/**
	 * Constructor for ImageDataTest.
	 * @param arg0
	 */
	public AbstractImageTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected abstract void setUp() throws Exception;

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
        image = null;
	}
    
    public void testSetGetColor() {
        //Writing the colors
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) {
                for (int k = image.getMinZ(), l = image.getMaxZ(); k <= l; k++) {
                    image.setColor(i, j, k, i*j+k);
                }
            }
        }
        
        //Reading the colors
        int[] point = new int[3];
        int size = image.getNVoxels();
        int color = 0;
        for (int i = 0; i < size; i++) {
            image.getCoordinates(i, point);
            color = image.getColor(point[0], point[1], point[2]);
            assertEquals(color, point[0]*point[1]+point[2]);
        }        
    }

}
