/*
 * LightInteger.java
 *
 * Created on 21. Februar 2002, 22:47
 */

package org.wewi.medimg.image.geom;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class ComparableInteger extends Number implements Comparable {
    private final int value;
    
    public ComparableInteger(int value) {
        this.value = value;
    }

    public int compareTo(Object obj) {
        ComparableInteger integer = (ComparableInteger)obj;
        return (value - integer.value);
    }
    
	/**
	 * @see java.lang.Number#doubleValue()
	 */
	public double doubleValue() {
		return (double)value;
	}

	/**
	 * @see java.lang.Number#floatValue()
	 */
	public float floatValue() {
		return (float)value;
	}

	/**
	 * @see java.lang.Number#intValue()
	 */
	public int intValue() {
		return value;
	}

	/**
	 * @see java.lang.Number#longValue()
	 */
	public long longValue() {
		return (long)value;
	}

}
