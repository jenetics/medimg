/**
 * Created on 31.08.2002
 *
 */
package org.wewi.medimg.alg;

/**
 * Interface for an iterateable Algorithm. If an Algorithm
 * implements this interface, it is possible "stop" at
 * every iteration and get an interim result. Running such an
 * algorithm class can be done as follows:
 * 
 * <pre>
 *      IterateableAlgorithm alg = new IterateableAlgorithmImpl();
 *      Object result = null;
 *      for (AlgorithmIterator it = alg.iterator(); it. hasNext();) {          
 *          it.next(); 
 *          result = it. getInterimResult();
 *          
 *          //Print the interim result to the standard out.
 *          System.out.println(result);
 *      }
 * </pre>
 * 
 * @see org.wewi.medimg.alg.IterateableAlgorithm
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public interface AlgorithmIterator {
    
    /**
     * Checks, whether there is an next iteration.
     * 
     * @return true, if there is an next iteration, false otherwise.
     */
    public boolean hasNext();
    
    /**
     * Performes the current iteration.
     */
    public void next();
    
    /**
     * Returns an interim result of the algorithm. Throws an 
     * <code>UnsupportedOperationException</code> if no interim 
     * result is available and therefore this method isn't implemented.
     * 
     * 
     * @return interim result of the algorithm.
     * 
     * @throws <code>UnsupportedOperationException</code> if no interim 
     *         result is available and therefore this method isn't implemented.
     */
    public Object getInterimResult() throws UnsupportedOperationException;
}
