/**
 * Created on 08.11.2002 13:08:46
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IntImageDataTest extends AbstractImageTest {

	/**
	 * Constructor for IntImageDataTest.
	 * @param arg0
	 */
	public IntImageDataTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        image = new IntImageData(2, 34, 543, 654, 3, 45);
	}

}
