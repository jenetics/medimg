/**
 * Created on 21.10.2002 23:15:16
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class IntData implements DiscreteData {
    private int[] data;

	/**
	 * Constructor for IntData.
	 */
	public IntData(int size) {
		super();
        data = new int[size];
	}

	/**
	 * @see org.wewi.medimg.image.DiscreteData#get(int)
	 */
	public int get(int pos) {
		return data[pos];
	}

	/**
	 * @see org.wewi.medimg.image.DiscreteData#set(int, int)
	 */
	public void set(int pos, int value) {
        data[pos] = value;
	}
    
    /**
     * @see org.wewi.medimg.image.DiscreteData#fill(int)
     */
    public void fill(int value) {
        Arrays.fill(data, value);
    }    


	/**
	 * @see org.wewi.medimg.image.DiscreteData#createCopy(DiscreteData)
	 */
	public void copy(DiscreteData target) {
        if (!(target instanceof IntData)) {
            return;    
        }
        
        IntData t = (IntData)target;
        
        System.arraycopy(data, 0, t.data, 0, t.data.length);
	}

}
