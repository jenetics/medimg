/*
 * RegistrationEvent.java
 *
 * Created on 19. April 2002, 15:46
 */

package org.wewi.medimg.reg.wizard;

import java.util.EventObject;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class RegistratorEvent extends EventObject {
    //private int iteration = 0;
    //private double[] meanValues = {};
    
    /** Creates a new instance of SegmentationEvent */
    public RegistratorEvent(Object source) {
        super(source);
    }
    
    /*public RegistrationEvent(Object source, int it, double[] mv) {
        super(source);
        iteration = it;
        meanValues = new double[mv.length];
        System.arraycopy(mv, 0, meanValues, 0, mv.length);        
    }*/
    
    /*public int getIteration() {
        return iteration;
    }
    
    public double[] getMeanValues() {
        return meanValues;
    }*/
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        /*buffer.append(iteration).append(": ");
        
        for (int i = 0; i < meanValues.length; i++) {
            String number = Double.toString(meanValues[i]);
            if (number.length() > 8) {
                number = number.substring(0, 8);
            }
            buffer.append("(").append(number).append(") ");
        }*/
        
        return buffer.toString();
    }    
}
