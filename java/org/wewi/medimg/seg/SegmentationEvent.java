/*
 * SegmentationEvent.java
 *
 * Created on 7. April 2002, 15:46
 */

package org.wewi.medimg.seg;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmentationEvent extends EventObject {
    private int iteration = 0;
    private double[] meanValues = {};
    
    /** Creates a new instance of SegmentationEvent */
    public SegmentationEvent(Object source) {
        super(source);
    }
    
    public SegmentationEvent(Object source, int it, double[] mv) {
        super(source);
        iteration = it;
        meanValues = new double[mv.length];
        System.arraycopy(mv, 0, meanValues, 0, mv.length);        
    }
    
    public int getIteration() {
        return iteration;
    }
    
    public double[] getMeanValues() {
        return meanValues;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(iteration).append(": ");
        
        for (int i = 0; i < meanValues.length; i++) {
            String number = Double.toString(meanValues[i]);
            if (number.length() > 8) {
                number = number.substring(0, 8);
            }
            buffer.append("(").append(number).append(") ");
        }
        
        return buffer.toString();
    }    
}
