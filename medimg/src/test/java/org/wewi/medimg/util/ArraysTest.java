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
 * ArraysTest.java
 * 
 * Created on 18.02.2003, 15:51:39
 *
 */
package org.wewi.medimg.util;

import java.util.Random;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ArraysTest extends TestCase {

	/**
	 * Constructor for ArraysTest.
	 * @param arg0
	 */
	public ArraysTest(String arg0) {
		super(arg0);
	}
    
    public void testIntPivotSort() {
        final int size = 200;
        Random rand = new Random(System.currentTimeMillis());
        
        int[] data1 = new int[size];
        int[] data2 = new int[size];
        int[] pivot = new int[size];
        
        for (int i = 0; i < size; i++) {
            data1[i] = rand.nextInt(10000);
            data2[i] = data1[i];
        }
        
        org.wewi.medimg.util.Arrays.sort(data1, pivot);
        java.util.Arrays.sort(data2);
        
        for (int i = 0; i < size; i++) {
            assertEquals("The sorted Arrays are not the same", 
                         data1[pivot[i]], data2[i]);
        }
        
    }

}
