/**
 * DirectComparison.java
 * 
 * Created on 30.12.2002, 14:26:20
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author werner weiser
 *
 */
public class DirectComparison {
    private AccumulatorArray accu;
    private final double N;

    /**
     * Constructor for MutualInformation.
     */
    public DirectComparison(AccumulatorArray accu) {
        if (accu == null) {
            throw new IllegalArgumentException("AccumulatorArray must not be null!");    
        }
        
        this.accu = accu;
        N = accu.elementSum();
    }
    
    /**
      */
    public double getDirectComparison(ColorRange cr1, ColorRange cr2) {
        if (N <= 0) {
            return 0;    
        }
        
        double dc = 0;
        int min = Math.max(cr1.getMinColor(), cr2.getMinColor());
        int max = Math.min(cr1.getMaxColor(), cr2.getMaxColor());
        for (int i = min; i <= max; i++) {
            dc += accu.getValue(i, i);    
        }
        return dc/N;        
    }
    

    

}