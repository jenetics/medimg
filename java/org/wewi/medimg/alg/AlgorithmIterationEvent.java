/**
 * Created on 10.09.2002
 *
 */
package org.wewi.medimg.alg;

import java.util.EventObject;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public class AlgorithmIterationEvent extends EventObject {
    private int iteration;
    
    public AlgorithmIterationEvent(Object source, int iteration) {
        super(source); 
        this.iteration = iteration;   
    }
    
    /**
     * Gets the number of the iterations so far.
     * 
     * @param number of iterations.
     */
    public int getIteration() {
        return iteration;    
    }
}
