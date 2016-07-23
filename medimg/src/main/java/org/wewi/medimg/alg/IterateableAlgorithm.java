/* 
 * IterateableAlgorithm.java, created on 20.10.2002 14:55:06
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
