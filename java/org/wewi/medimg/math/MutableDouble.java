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
public class MutableDouble extends java.lang.Number implements Mutable {
    private double value;

	/**
	 * Constructor for MutableDouble.
	 */
	public MutableDouble(double value) {
		this.value = value;
	}
    
    public MutableDouble() {
        this(0);    
    }
    
    
    public void setValue(double value) {
        this.value = value;    
    }
    
    public double getValue() {
        return value;   
    }
    
    /**
     * @see java.lang.Number#doubleValue().
     */
    public double doubleValue() {
        return value;
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
        return (int)value;
    }

    /**
     * @see java.lang.Number#longValue()
     */
    public long longValue() {
        return (long)value;
    }

}
