/* 
 * AlgorithIterator.java, ceated on 31.08.2002
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
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
