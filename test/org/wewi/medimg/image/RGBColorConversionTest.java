/**
 * Created on 08.11.2002 13:25:33
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RGBColorConversionTest extends AbstractColorConversionTest {

	/**
	 * Constructor for FGBColorConversionTest.
	 * @param arg0
	 */
	public RGBColorConversionTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        cc = new RGBColorConversion();
	}

}
