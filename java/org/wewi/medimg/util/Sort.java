/**
 * Sort.java
 * 
 * Created on 17.02.2003, 22:26:46
 *
 */
package org.wewi.medimg.util;

import java.util.Random;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class Sort {

	private Sort() {
		super();
	}
    
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
    
    
    public static void main(String[] args) {
        Random rand = new Random(System.currentTimeMillis());
        
        int[] data = new int[20];
        int[] pivot = new int[20];
        for(int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt(1000);
        }
        
        sort(data, pivot);
        
        for (int i = 0; i < data.length; i++) {
            System.out.println("" + data[i] + " : " + data[pivot[i]]);
        }
        
    }

}
