/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.util;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableInteger implements Mutable {
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

}
