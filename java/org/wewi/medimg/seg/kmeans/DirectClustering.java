/*
 * DirectClustering.java
 *
 * Created on 5. Februar 2002, 14:04
 */

package org.wewi.medimg.seg.kmeans;

import org.wewi.medimg.seg.SegmentationStrategy;
import org.wewi.medimg.seg.SegmentationListener;

import java.util.Random;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class DirectClustering {
    
    private class DirectClusteringIterator implements Iterator {
        private double ERROR_LIMIT = 0.1;
        
        private int k;
        private Collection data;
        private DataPoint[] clusterCenter;
        private DirectClustering parent;

        private DataPoint[] clusterSum;
        private int[] clusterNo;
        private double error = Double.MAX_VALUE, error0 = 0;
        private int iterationCount = 0;        
        
        public DirectClusteringIterator(Collection data, int k, DirectClustering parent) {
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
        
        public boolean hasNext() {
            return Math.abs(error0 - error) > ERROR_LIMIT;
        }
        
        public Object next() {           
            m1Step();
            m2Step();
            return parent;            
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
            //System.out.println("error0: " + error0 + " error: " + error);
        }  
        
        private void m2Step() {
            for (int i = 0; i < k; i++) {
                clusterCenter[i] = clusterSum[i].div(clusterNo[i]);
            }
        } 
        
        public DataPoint[] getClusterCenter() {
            //DataPoint[] ret = new DataPoint[k];
            //System.arraycopy(clusterCenter, 0, ret, 0, k);
            //return ret;
            return clusterCenter;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    private class Clusterer {
        private DataPoint[] center;
        
        public Clusterer(DataPoint[] center) {
            this.center = center;
        }
        
        public Collection[] getCluster(Collection data) {
            Vector[] cluster = new Vector[center.length];
            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = new Vector();
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
    private Collection data;
    private DirectClusteringIterator clusteringIterator;
    
    public DirectClustering(Collection data, int k) {
        this.k = k;
        this.data = data;
        
        clusteringIterator = new DirectClusteringIterator(data, k, this);
    }
    
    public Iterator getClusterIterator() {
        clusteringIterator = new DirectClusteringIterator(data, k, this);
        return clusteringIterator;
    }
    
    public void cluster() {
        clusteringIterator = new DirectClusteringIterator(data, k, this);
        while (clusteringIterator.hasNext()) {
            clusteringIterator.next();
        }
    }
    
    public Collection[] getCluster() {
        Clusterer clusterer = new Clusterer(clusteringIterator.getClusterCenter());
        return clusterer.getCluster(data);
    }
    
    public DataPoint[] getClusterCenter() {
        return clusteringIterator.getClusterCenter();
    }
    
}
