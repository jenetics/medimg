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
 * Created on 19.11.2002 16:58:39
 *
 */
package org.wewi.medimg.image.geom;

import org.wewi.medimg.math.vec.DoubleGridVectorField;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GridVectorFieldTest extends TestCase {
    private DoubleGridVectorField field;

	/**
	 * Constructor for GridVectorFieldTest.
	 * @param arg0
	 */
	public GridVectorFieldTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        field = new DoubleGridVectorField(new Point3D(30, 23, -23), 
                                       new int[]{20, 12, 54},
                                       new int[]{1, 3, 23});
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    

}
