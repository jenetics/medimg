/**
 * Created on 08.11.2002 13:11:07
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ByteImageDataTest extends AbstractImageTest {

	/**
	 * Constructor for ByteImageDataTest.
	 * @param arg0
	 */
	public ByteImageDataTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        image = new ByteImageData(0, 6, 0, 13, 1, 8);
	}

}
