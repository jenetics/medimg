/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.math;

import org.wewi.medimg.util.Mutable;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableInteger extends Number implements Mutable {
    private int value;

	/**
	 * Constructor for MutableInteger.
	 */
	public MutableInteger(int value) {
        this.value = value;
	}
    
    public MutableInteger() {
        this(0);
    }
    
    public void setValue(int value) {
        this.value = value;    
    }
    
    public int getValue() {
        return value;    
    }
    
    public void inc() {
        ++value;    
    }
    
    public void dec() {
        --value;    
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
