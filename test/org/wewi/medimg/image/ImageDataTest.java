/**
 * Created on 08.11.2002 13:05:07
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageDataTest extends AbstractImageTest {

	/**
	 * Constructor for ImageDataTest.
	 */
	public ImageDataTest(String name) {
		super(name);
	}


	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        image = new ImageData(23, 134, 1, 43, 345, 543);
	}
    
    public void testGetPositionGetCoordinate() {
        int pos = image.getPosition(28, 34, 350);
        int[] c = new int[3];
        image.getCoordinates(pos, c);
        
        assertEquals(28, c[0]);
        assertEquals(34, c[1]);
        assertEquals(350, c[2]);    
    }    

}
