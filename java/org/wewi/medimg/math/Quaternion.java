/**
 * Quaternion.java
 * 
 * Created on 17.03.2003, 22:28:46
 *
 */
package org.wewi.medimg.math;

import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Quaternion implements Immutable {
    
    double r;
    double i;
    double j;
    double k;

	/**
	 * Constructor for Quaternion.
	 */
	public Quaternion(double r, double i, double j, double k) {
		this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
	}
    
    public Quaternion(double r) {
        this(r, 0, 0, 0);
    }
    
    public Quaternion add(Quaternion q){
        return null;
    }
    
    public Quaternion sub(Quaternion q) {
        return null;
    }
    
    public Quaternion mult(Quaternion q) {
        return null;
    }
    
    public Quaternion div(Quaternion q) {
        return null;
    }

}
