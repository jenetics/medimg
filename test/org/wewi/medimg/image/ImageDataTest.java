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

}
