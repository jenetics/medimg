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
