/**
 * Created on 20.10.2002 14:55:06
 *
 */
package org.wewi.medimg.alg;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface IterateableAlgorithm {
    public AlgorithmIterator getAlgorithmIterator();
    
    public int getIterations();
}
