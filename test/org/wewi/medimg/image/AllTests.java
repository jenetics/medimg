/**
 * Created on 08.11.2002 13:12:46
 *
 */
package org.wewi.medimg.image;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AllTests {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(AllTests.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.wewi.medimg.image");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(ImageDataTest.class));
		suite.addTest(new TestSuite(IntImageDataTest.class));
		suite.addTest(new TestSuite(ShortImageDataTest.class));
		suite.addTest(new TestSuite(ByteImageDataTest.class));
        
        suite.addTest(new TestSuite(RGBColorConversionTest.class));
        suite.addTest(new TestSuite(RGBAColorConversionTest.class));
		//$JUnit-END$
		return suite;
	}
}
