/**
 * Arrays.java
 * 
 * Created on 17.02.2003, 22:26:46
 *
 */
package org.wewi.medimg.util;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class Arrays {

	private Arrays() {
		super();
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
                //data[i + 1] = data[i];
                i = i - 1;
            }
            
            //data[i + 1] = key;
            pivot[i + 1] = j;
        }
    }
 
}
