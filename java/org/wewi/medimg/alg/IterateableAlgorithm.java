/**
 * Created on 20.10.2002 14:55:06
 *
 */
package org.wewi.medimg.alg;

/**
 * This interface shows that the algorithm works iterative and
 * that the iterations can be done by an <code>AlgorithmIterator</code>.
 * 
 * @see org.wewi.medimg.alg.AlgorithmIterator
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 * @since 0.1
 */
public interface IterateableAlgorithm {
    
    /**
     * Returns the <code>AlgorithmIterator</code> of this
     * iterateable algorithm.
     * 
     * @return <code>AlgorithmIterator</code> of this algorithm.
     */
    public AlgorithmIterator iterator();
    
    /**
     * Returns the number of iterations performed so far.
     * 
     * @return number of iterations.
     */
    public int getIterations();
}
