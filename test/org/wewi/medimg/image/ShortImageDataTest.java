/**
 * Created on 08.11.2002 13:10:07
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ShortImageDataTest extends AbstractImageTest {

	/**
	 * Constructor for ShortImageDataTest.
	 * @param arg0
	 */
	public ShortImageDataTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        image = new ShortImageData(345, 543, 3, 54, 321, 453);
	}

}
