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
public interface InterruptableAlgorithm {
    
    public void interruptAlgorithm() throws UnsupportedOperationException;
    
    public void resumeAlgorithm() throws UnsupportedOperationException;
    
    public void cancelAlgorithm() throws UnsupportedOperationException;
}
