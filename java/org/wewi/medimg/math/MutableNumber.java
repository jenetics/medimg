/**
 * MutableNumber.java
 * Created on 28.04.2003
 *
 */
package org.wewi.medimg.math;


import org.wewi.medimg.util.Mutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class MutableNumber extends Number 
                                    implements Comparable,
                                               Mutable, 
                                               Cloneable {

    public void setValue(byte n) {
        setValue((int)n);
    }
     
    public void setValue(short n) {
        setValue((int)n);
    }

    public abstract void setValue(int n);
    
    public abstract void setValue(long n);

    public abstract void setValue(float n);
    
    public abstract void setValue(double n);
    
    public abstract void setValue(Number n);

    public abstract Object clone();
}
