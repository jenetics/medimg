/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.util;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableDouble implements Mutable {
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
    
}
