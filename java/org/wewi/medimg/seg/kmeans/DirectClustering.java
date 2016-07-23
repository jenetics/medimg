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
 * DirectClustering.java
 *
 * Created on 5. Februar 2002, 14:04
 */

package org.wewi.medimg.seg.kmeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.wewi.medimg.alg.AlgorithmIterationEvent;
import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.alg.IterateableAlgorithm;
import org.wewi.medimg.alg.ObservableAlgorithm;
import org.wewi.medimg.math.geom.DataPoint;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class DirectClustering extends ObservableAlgorithm 
                                      implements DataPointClusterer,
                                                  IterateableAlgorithm {
    
    private final class DirectClusteringIterator implements AlgorithmIterator {

        public DirectClusteringIterator() {
            init();
        }       
          
        public boolean hasNext() {
            return Math.abs(error0 - error) > ERROR_LIMIT;
        }
        
        public void next() {  
            ++iterationCount;         
            iteration();           
        }
        
        /**
         * @see org.wewi.medimg.alg.AlgorithmIterator#getInterimResult()
         */
        public Object getInterimResult() throws UnsupportedOperationException {
            Clusterer clusterer = new Clusterer(clusterCenter);
            return clusterer.getCluster(data);
        }

    }
    
    /**
     * Diese Klasse ist für die wirkliche Aufteilung
     * der Datenmenge zuständig.
     */
    private final class Clusterer {
        private DataPoint[] center;
        
        public Clusterer(DataPoint[] center) {
            this.center = center;
        }
        
        public Collection[] getCluster(Collection data) {
            Collection[] cluster = new Collection[center.length];
            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = new ArrayList();
            }
            
            double distance, minDistance;
            int minCluster;

            DataPoint point;
            for (Iterator it = data.iterator(); it.hasNext();) {
                point = (DataPoint)it.next();

                minDistance = Double.MAX_VALUE;
                minCluster = 0;
                for (int c = 0; c < center.length; c++) {
                    distance = point.distance(center[c]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        minCluster = c;
                    }
                }
                cluster[minCluster].add(point);
            }            
            return cluster;
        }
    }
    
   
    private double ERROR_LIMIT = 0.1;
    private int k;
    private Collection data;
    
    private DataPoint[] clusterCenter;
    private DataPoint[] clusterSum;
    private int[] clusterNo;
    private double error = Double.MAX_VALUE, error0 = 0;
    private int iterationCount = 0;     
    
    public DirectClustering(int k, Collection data) {
        if (k > data.size()) {
            throw new IllegalArgumentException("Number of cluster (" + k + 
                                                 ") is bigger than the number of datapoints (" +
                                                 data.size() + ")");    
        }
        
        this.k = k;
        this.data = data;
    }
    
    private void init() {
        error = Double.MAX_VALUE; 
        error0 = 0;
        iterationCount = 0; 
        
        //Initialisieren der Clusterzentren
        Iterator it = data.iterator();
        for (int i = 0; i < k; i++) {
            clusterCenter[i] = (DataPoint)it.next();    
        }       
    }
    
    public Collection[] cluster() { 
       DirectClusteringIterator iterator = new DirectClusteringIterator();
        
        while (iterator.hasNext()) {
            iterator.next();
        }
        
        Clusterer clusterer = new Clusterer(clusterCenter);
        return clusterer.getCluster(data);        
    }    
    
    private void iteration() {
        notifyIterationStarted(new AlgorithmIterationEvent(this, iterationCount));
        
        m1Step();
        m2Step();
        
        notifyIterationFinished(new AlgorithmIterationEvent(this, iterationCount));        
    }
    
    
    private void m1Step() {        
        double distance;
        double minDistance;
        int minCluster;

        //Initialisieren der Clustersumme
        for (int i = 0; i < k; i++) {
            clusterSum[i] = clusterCenter[i].getNullInstance();
            clusterNo[i] = 0;
        }  
        
        int err = 0;
        DataPoint point;
        for (Iterator it = data.iterator(); it.hasNext();) {
            point = (DataPoint)it.next();

            minDistance = Double.MAX_VALUE;
            minCluster = 0;
            for (int c = 0; c < k; c++) {
                distance = point.distance(clusterCenter[c]);
                if (distance < minDistance) {
                    minDistance = distance;
                    minCluster = c;
                }
            }

            clusterSum[minCluster] = clusterSum[minCluster].add(point);
            clusterNo[minCluster]++;
            err += point.distance(clusterCenter[minCluster]);
        } 
        
        error0 = error;
        error = err;
    }  
    
    private void m2Step() {
        for (int i = 0; i < k; i++) {
            clusterCenter[i] = clusterSum[i].scale(clusterNo[i]);
        }
    }    
    
    public AlgorithmIterator iterator() {
        return new DirectClusteringIterator();
    }
    
    public int getIterations() {
        return iterationCount;    
    }    
    
}







