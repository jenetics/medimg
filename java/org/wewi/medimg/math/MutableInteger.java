/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableInteger extends MutableNumber {
    private int value;
    
    public MutableInteger() {
    }
    
    public MutableInteger(int v) {
        value = v;
    }
    
    public void inc() {
        ++value;
    }
    
    public void dec() {
        --value;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(byte)
     */
    public void setValue(byte n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(short)
     */
    public void setValue(short n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(int)
     */
    public void setValue(int n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(long)
     */
    public void setValue(long n) {
        value = (int)n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(float)
     */
    public void setValue(float n) {
        value = Math.round(n);
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(double)
     */
    public void setValue(double n) {
        value = (int)Math.rint(n);
    }
    
    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(org.wewi.medimg.math.Number)
     */
    public void setValue(Number n) {
        value = intValue();
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#clone()
     */
    public Object clone() {
        return new MutableInteger(value);
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
        return value;
    }

    /**
     * @see java.lang.Number#floatValue()
     */
    public float floatValue() {
        return value;
    }

    /**
     * @see java.lang.Number#doubleValue()
     */
    public double doubleValue() {
        return value;
    }

}
