/**
 * NormalizedMutualInformationOperator.java
 * 
 * Created on 23.12.2002, 14:56:12
 *
 */ 
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author werner weiser
 *
 */
public class NormalizedMutualInformationOperator implements BinaryOperator {

    private ColorRange cr1;
    private ColorRange cr2;
    
    private AccumulatorArray accu;

    /**
     * Constructor for NormalizedMutualInformationOperator.
     */
    public NormalizedMutualInformationOperator(ColorRange cr1, ColorRange cr2) {
        this.cr1 = cr1;
        this.cr2 = cr2;
        
        accu = new AccumulatorArray(cr1.getNColors(), cr2.getNColors());
    }

    /**
     * @see org.wewi.medimg.image.ops.BinaryOperator#process(int, int)
     */
    public void process(int color1, int color2) {
        accu.inc(color1 - cr1.getMinColor(), color2 - cr2.getMinColor());
    }
    
    
    public double getMutualInformation() {
        NormalizedMutualInformation nmi = new NormalizedMutualInformation(accu);
        return nmi.getNormalizedMutualInformation();   
    }  

}