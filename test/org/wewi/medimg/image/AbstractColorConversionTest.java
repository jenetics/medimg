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
 * Created on 08.11.2002 13:18:40
 *
 */
package org.wewi.medimg.image;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class AbstractColorConversionTest extends TestCase {
    protected ColorConversion cc;

	/**
	 * Constructor for ColorConversionTest.
	 * @param arg0
	 */
	public AbstractColorConversionTest(String arg0) {
		super(arg0);   
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected abstract void setUp() throws Exception;
    
    public void testConversion() {
        int grey = 113;
        int[] rgb = new int[4];
        
        cc.convert(grey, rgb);
        grey = cc.convert(rgb);
        assertEquals("Colors are not the same", 113, grey); 
        
        rgb[0] = 13;
        rgb[1] = 43;
        rgb[2] = 123;
        grey = cc.convert(rgb);
        cc.convert(grey, rgb);
        assertEquals("Color red", 13, rgb[0]);
        assertEquals("Color blue", 43, rgb[1]);
        assertEquals("Color green", 123, rgb[2]);   
    }

}
