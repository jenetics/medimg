/*
 * Range.java
 *
 * Created on 15. März 2002, 16:32
 */

package org.wewi.medimg.image;

/**
 *
 * @author  fwilhelm
 * @version 
 */
public final class Range {
    private int min;
    private int max;

    /** Creates new Range */
    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    public Range(Range range) {
        this.min = range.min;
        this.max = range.max;
    }

    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
}
