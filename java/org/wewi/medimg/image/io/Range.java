/**
 * Range.java
 *
 * Created on 10. April 2002, 23:08
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.util.Immutable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Range implements Immutable {
    private final int minSlice;
    private final int maxSlice;
    private final int stride;
    
    public Range(int minSlice, int maxSlice) throws IllegalArgumentException {
        this(minSlice, maxSlice, 1);
    }
    
    public Range(int minSlice, int maxSlice, int stride) {
        if (minSlice > maxSlice) {
            throw new IllegalArgumentException("minSlice is greater then maxSlice: \n" +
                                                "minSlice: " + minSlice + 
                                                ", maxSlice: " + maxSlice);
        }
        if (stride <= 0) {
            throw new IllegalArgumentException("Stride is less or equal zero:\n" +
                                                "stride: " + stride);
        }
        this.minSlice = minSlice;
        this.maxSlice = maxSlice;
        this.stride = stride;
    }
    
    public int getMinSlice() {
        return minSlice;
    }
    
    public int getMaxSlice() {
        return maxSlice;
    }
    
    public int getStride() {
        return stride;
    }
}
