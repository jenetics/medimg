/**
 * Created on 10.09.2002
 *
 */
package org.wewi.medimg.alg;

import java.util.EventObject;

/**
 * This Object represents an iteration event. This event is sent, when the
 * algorithm starts a new iteration.
 *
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AlgorithmIterationEvent extends EventObject {
    private int iteration;
    
    /**
     * Constructing a new AlgorithmIterationEvent with the source algorithm and
     * the number of iterations the algorithm has performed so far.
     * 
     * @param source algorithm object.
     * @param iteration number of iterations so far.
     */    
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
