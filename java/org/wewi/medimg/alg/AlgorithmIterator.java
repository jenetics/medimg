/**
 * Created on 31.08.2002
 *
 */
package org.wewi.medimg.alg;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public interface AlgorithmIterator {
    
    public boolean hasNextIteration();
    
    public void nextIteration();
    
    public Object getInterimResult() throws UnsupportedOperationException;
}
