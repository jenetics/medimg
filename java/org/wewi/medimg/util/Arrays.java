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
 * Arrays.java
 * 
 * Created on 17.02.2003, 22:26:46
 *
 */
package org.wewi.medimg.util;

import java.util.Comparator;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class Arrays {

    private Arrays() {
        super();
    }
    
    
    public static void copy(double[] src, int srcPos, float[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[i + destPos] = (float)src[i + srcPos];
        }
    }
    
    public static void copy(double[] src, int srcPos, int[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[i + destPos] = (int)Math.round(src[i + srcPos]);
        }
    }
    
    /**
     * Insertion sort algorithm. Instead of the <code>data</code> array, the
     * <code>pivot</code> array is changed so that the "array" <code>data[pivot
     * [i]]</code> is sorted. The <code>data</code> array will not be touched.
     * 
     * @param data
     * @param pivot
     */
    public static void sort(int[] data, int[] pivot) {
        for (int i = 0, n = data.length; i < n; i++) {
            pivot[i] = i;
        }
        
        int key = 0, i = 0;
        for (int j = 1, n = data.length; j < n; j++) {
            key = data[pivot[j]];
            i = j - 1;
            
            while (i >= 0 && data[pivot[i]] > key) {
                pivot[i + 1] = pivot[i];
                i = i - 1;
            }
            
            pivot[i + 1] = j;
        }
    }
    
    public static void sort(Object[] data, int[] pivot, Comparator comp) {
        for (int i = 0, n = data.length; i < n; i++) {
            pivot[i] = i;
        }
        
        Object key = null;
        int i = 0;
        for (int j = 1, n = data.length; j < n; j++) {
            key = data[pivot[j]];
            i = j - 1;
            
            while (i >= 0 && comp.compare(data[pivot[i]], key) > 0) {
                pivot[i + 1] = pivot[i];
                i = i - 1;
            }

            pivot[i + 1] = j;
        }        
    }
 
}







