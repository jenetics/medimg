/**
 * Created on 08.10.2002 12:51:33
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class Permutation {
    private int[] length;
    
    private int[] current;
    private long pos;
    private long l;

	/**
	 * Constructor for Permutation.
	 */
	public Permutation(int[] length) {
		super();
        this.length = length;
        
        current = new int[length.length];
        Arrays.fill(current, 0);
        
        l = 1;
        for (int i = 0; i < current.length; i++) {
            l *= length[i];    
        }

	}
    
    public boolean hasNext() {
        return pos < l-1;    
    }
    
    
    public void next(int[] n) {
        pos++;
        int p = 0;
        if (current[p] < length[p]-1) {
            current[p]++;    
        } else {
            while (current[p] >= length[p]-1) {
                if (p < length.length-1) {
                    p++;
                    for (int i = 0; i < p; i++) {
                        current[i] = 0;    
                    }    
                } else {
                    return;    
                }
            }
            
            current[p]++;
        }
        
        System.arraycopy(current, 0, n, 0, n.length);
    }
    
    
    public static void main(String[] args) {
        int[] size = {5,3,1};
        int[] erg = new int[size.length];
        
        Permutation perm = new Permutation(size);
        for (int i = 0; i < 50; i++) {
            while (perm.hasNext()) {
                perm.next(erg);   
                for (int j = 0; j < erg.length; j++) {
                    System.out.print("|" + erg[j]);    
                }
                System.out.print("\n");
            }
        }    
    }

}





















