/**
 * Created on 22.10.2002 00:03:26
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ByteData implements DiscreteData {
    private byte[] data;

	/**
	 * Constructor for ByteData.
	 */
	public ByteData(int size) {
		super();
        data = new byte[size];
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
        data[pos] = (byte)value;
	}

	/**
	 * @see org.wewi.medimg.image.DiscreteData#fill(int)
	 */
	public void fill(int value) {
        Arrays.fill(data, (byte)value);
	}

	/**
	 * @see org.wewi.medimg.image.DiscreteData#copy(DiscreteData)
	 */
	public void copy(DiscreteData target) {
        if (!(target instanceof ByteData)) {
            return;    
        }
        
        ByteData t = (ByteData)target;
        
        System.arraycopy(data, 0, t.data, 0, t.data.length);        
	}

}
