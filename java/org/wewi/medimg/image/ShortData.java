/**
 * Created on 22.10.2002 00:03:03
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ShortData implements DiscreteData {
    private short[] data;

	/**
	 * Constructor for ShortData.
	 */
	public ShortData(int size) {
		super();
        data = new short[size];
	}

	/**
	 * @see org.wewi.medimg.image.DiscreteData#get(int)
	 */
	public int get(int pos) {
		return data[pos];
	}
    
    public int getInt(int pos) {
        return data[pos];
    }
    
    public double getDouble(int pos) {
        return data[pos];
    }

	/**
	 * @see org.wewi.medimg.image.DiscreteData#set(int, int)
	 */
	public void set(int pos, int value) {
        data[pos] = (short)value;
	}
    
    public void set(int pos, double value) {
        data[pos] = (short)value;
    }

	/**
	 * @see org.wewi.medimg.image.DiscreteData#fill(int)
	 */
	public void fill(int value) {
        Arrays.fill(data, (short)value);
	}
    
    public void fill(double value){
        Arrays.fill(data, (short)value);
    }

	/**
	 * @see org.wewi.medimg.image.DiscreteData#copy(DiscreteData)
	 */
	public void copy(DiscreteData target) {
        if (!(target instanceof ShortData)) {
            return;    
        }
        
        ShortData t = (ShortData)target;
        
        System.arraycopy(data, 0, t.data, 0, t.data.length);        
	}

}
