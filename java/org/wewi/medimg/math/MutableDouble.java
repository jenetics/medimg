/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableDouble extends MutableNumber {
    private double value;
    
    public MutableDouble(double v) {
        value = v;
    }
    
    public MutableDouble() {
        value = 0;
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
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(float)
     */
    public void setValue(float n) {
        value = n;        
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(double)
     */
    public void setValue(double n) {
        value = n;    
    }
    
    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(org.wewi.medimg.math.Number)
     */
    public void setValue(Number n) {
        value = n.doubleValue();
    }
    
    /**
     * @see java.lang.Number#intValue()
     */
    public int intValue() {
        return (int)Math.round(value);
    }

    /**
     * @see java.lang.Number#longValue()
     */
    public long longValue() {
        return Math.round(value);
    }

    /**
     * @see java.lang.Number#floatValue()
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * @see java.lang.Number#doubleValue()
     */
    public double doubleValue() {
        return value;
    }  
    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        MutableDouble mi = (MutableDouble)o;
        if (value > mi.value) {
            return 1;
        } else if (value < mi.value) {
            return -1;
        } else {
            return 0;
        }
    }  

    /**
     * @see org.wewi.medimg.math.MutableNumber#clone()
     */
    public Object clone() {
        return new MutableDouble(value);
    }

}
