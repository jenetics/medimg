/**
 * DoubleInterval.java
 * Created on 26.05.2003
 *
 */
package org.wewi.medimg.util.param;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleInterval extends Number {
    private double value;

    private Double d;
    public DoubleInterval() {
        super();
    }
    
    public void checkInterval() throws IllegalArgumentException {
        throw new IllegalArgumentException();
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
        return (long)Math.round(value);
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

}
