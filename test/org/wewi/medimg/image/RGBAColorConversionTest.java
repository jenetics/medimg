/**
 * Created on 08.11.2002 13:29:38
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RGBAColorConversionTest extends AbstractColorConversionTest {

	/**
	 * Constructor for RGBAColorConversion.
	 * @param arg0
	 */
	public RGBAColorConversionTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
        cc = new RGBAColorConversion();
	}

}
