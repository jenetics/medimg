/*
 * DirectClustering.java
 *
 * Created on 5. Februar 2002, 14:04
 */

package org.wewi.medimg.seg.kmeans;

import java.util.Iterator;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.math.geom.*;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class DirectClustering implements DataPointClusterer {
    
    private class DirectClusteringIterator implements AlgorithmIterator {
        private double ERROR_LIMIT = 0.1;
        
        private int k;
        private DataPointCollection data;
        private DataPoint[] clusterCenter;
        private DirectClustering parent;

        private DataPoint[] clusterSum;
        private int[] clusterNo;
        private double error = Double.MAX_VALUE, error0 = 0;
        private int iterationCount = 0;        
        
        public DirectClusteringIterator(DataPointCollection data, int k, DirectClustering parent) {
            this.data = data;
            this.k = k;
            this.parent = parent;
            
            clusterCenter = new DataPoint[k];
            clusterSum= new DataPoint[k];
            clusterNo = new int[k];            
            
            int i = 0;
            for (Iterator it = data.iterator(); it.hasNext();) {
                clusterCenter[i++] = (DataPoint)it.next();
                if (i == k) {
                    return;
                }
            }
        }      
        
        private void m1Step() {
            int size = data.size();

            DataPoint point;
            double distance;
            double minDistance;
            int minCluster;

            for (int i = 0; i < k; i++) {
                clusterSum[i] = clusterCenter[i].getNullInstance();
                clusterNo[i] = 0;
            }  
            
            int err = 0;
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
                clusterCenter[i] = clusterSum[i].div(clusterNo[i]);
            }
        } 
        
        public boolean hasNextIteration() {
            return Math.abs(error0 - error) > ERROR_LIMIT;
        }
        
        public void nextIteration() {           
            m1Step();
            m2Step();            
        }
        
		/**
		 * @see org.wewi.medimg.alg.AlgorithmIterator#getInterimResult()
		 */
		public Object getInterimResult() throws UnsupportedOperationException {
            Clusterer clusterer = new Clusterer(clusterCenter);
			return clusterer.getCluster(data);
		}

    }
    
    private class Clusterer {
        private DataPoint[] center;
        
        public Clusterer(DataPoint[] center) {
            this.center = center;
        }
        
        public DataPointCollection[] getCluster(DataPointCollection data) {
            DataPointCollection[] cluster = new DataPointCollection[center.length];
            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = new DataPointCollection();
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
    
    
    private int k;
    private DirectClusteringIterator clusteringIterator;
    
    public DirectClustering(int k) {
        this.k = k;
    }
    
    public AlgorithmIterator getClusterIterator(DataPointCollection data) {
        clusteringIterator = new DirectClusteringIterator(data, k, this);
        return clusteringIterator;
    }
    
    public DataPointCollection[] cluster(DataPointCollection data) {
        clusteringIterator = new DirectClusteringIterator(data, k, this);
        while (clusteringIterator.hasNextIteration()) {
            clusteringIterator.nextIteration();
        }
        
        return (DataPointCollection[])clusteringIterator.getInterimResult();
    }
    
    public DataPoint[] getClusterCenter() {
        return (DataPoint[])clusteringIterator.getInterimResult();
    }
    
}







