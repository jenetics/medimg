/**
 * Created on 21.08.2002
 *
 */
package org.wewi.medimg.alg;

import java.util.EventListener;


/**
 * Interface for an Algorithm
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface AlgorithmIterationListener extends EventListener {
    
    public void iterationStarted(AlgorithmIterationEvent event);
    
    public void iterationFinished(AlgorithmIterationEvent event);
}
