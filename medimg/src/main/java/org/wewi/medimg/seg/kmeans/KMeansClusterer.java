/* 
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

/**
 * KMeansClusterer.java
 * 
 * Created on 26.02.2003, 23:49:42
 *
 */
package org.wewi.medimg.seg.kmeans;

import java.util.Collection;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.alg.IterateableAlgorithm;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class KMeansClusterer implements IterateableAlgorithm {
    
    private Collection data;
    private int k;

    /**
     * Constructor for KMeansClusterer.
     */
    public KMeansClusterer(Collection data, int k) {
        this.data = data;
        this.k = k;
    }
    /**
     * @see org.wewi.medimg.alg.IterateableAlgorithm#iterator()
     */
    public AlgorithmIterator iterator() {
        return null;
    }
    /**
     * @see org.wewi.medimg.alg.IterateableAlgorithm#getIterations()
     */
    public int getIterations() {
        return 0;
    }

}
