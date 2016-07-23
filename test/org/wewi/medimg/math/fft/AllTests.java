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
 * AllTests.java
 * 
 * Created on 20.12.2002, 15:59:24
 *
 */
package org.wewi.medimg.math.fft;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.wewi.medimg.math.fft");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(IterativeFFT1DTest.class));
        suite.addTest(new TestSuite(NaiveDFT1DTest.class));
        suite.addTest(new TestSuite(RecursiveFFT1DTest.class));
        suite.addTest(new TestSuite(DFT2DTest.class));
        suite.addTest(new TestSuite(DFT3DTest.class));
        //$JUnit-END$
        return suite;
    }
}
