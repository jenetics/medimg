/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

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
		suite.addTest(new TestSuite(IntImageTest.class));
		suite.addTest(new TestSuite(IntImageTest.class));
		suite.addTest(new TestSuite(ShortImageTest.class));
		suite.addTest(new TestSuite(ByteImageTest.class));
        
        suite.addTest(new TestSuite(RGBColorConversionTest.class));
        suite.addTest(new TestSuite(RGBAColorConversionTest.class));
		//$JUnit-END$
		return suite;
	}
}
