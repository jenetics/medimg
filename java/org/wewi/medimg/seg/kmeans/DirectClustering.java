/*
 * DirectClustering.java
 *
 * Created on 5. Februar 2002, 14:04
 */

package org.wewi.medimg.seg.kmeans;

import org.wewi.medimg.seg.SegmentationStrategy;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class DirectClustering implements SegmentationStrategy {
    private double ERROR_LIMIT = 0.1;
    private int iterationCount;
    
    private int nclusters;
    private DataPoint[] data;
    private DataPoint[] clusterCenter;
    private byte[] cluster;

    public DirectClustering(DataPoint[] data, byte nclusters) {
        this.data = data;
        this.nclusters = nclusters;
        clusterCenter = new DataPoint[nclusters];
        
        cluster = new byte[data.length];
        for (int i = 0; i < cluster.length; i++) {
            cluster[i] = 0;
        }
    }
    
    private boolean contains(DataPoint point) {
        if (point == null) {
            return true;
        }
        for (int i = 0; i < clusterCenter.length; i++) {
            if (point.equals(clusterCenter[i])) {
                return true;
            }
        }
        return false;
    }
    
    private void init() {
        Random rand = new Random(System.currentTimeMillis());
        int count = 0;
        int dataPointer = rand.nextInt(data.length);
        do {
            if (contains(data[dataPointer])) {
                //dataPointer++;
            } else {
                clusterCenter[count] = data[dataPointer];
                count++;
                //dataPointer++;
            }
            dataPointer = rand.nextInt(data.length);
        } while (count < nclusters);
    }
    
    private void m1Step() {
        int size = data.length;
        DataPoint point;
        double minDistance;
        double tempDistance;
        byte minClusterIndex;
        //Berechnen der Bereichszugehörigkeit///////////////////////////////////
        for (int i = 0; i < size; i++) {
            point = data[i];
            minDistance = Double.MAX_VALUE;
            minClusterIndex = 0;
            for (byte j = 0; j < nclusters; j++) {
                tempDistance = point.distance(clusterCenter[j]);
                if (tempDistance < minDistance) {
                    minClusterIndex = (byte)j;
                    minDistance = tempDistance;
                }
            }
            cluster[i] = minClusterIndex;
        }
        ////////////////////////////////////////////////////////////////////////        
    }
    
    private void m2Step() {
        int size = data.length;
        DataPoint point;
        int[] ccount = new int[nclusters];
        byte f;        
        //Berechnen der neuen Bereichsrepräsentanten////////////////////////////
        for (int i = 0; i < nclusters; i++) {
            ccount[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            f = cluster[i];
            point = data[i];
            ccount[f]++;
            clusterCenter[f] = clusterCenter[f].add(point);
        }
        for (int i = 0; i < nclusters; i++) {
            clusterCenter[i] = clusterCenter[i].div((double)ccount[i]);
        }
        ////////////////////////////////////////////////////////////////////////        
    }
    
    private double error() {
        int size = data.length;
        double e = 0;
        for (int i = 0; i < size; i++) {
            e += data[i].sub(clusterCenter[cluster[i]]).norm();
        }
        return e;
    }
    
    public void doSegmentation() {
        double e = 0, eo = 0;
        iterationCount = 0;
        init();
        m1Step();
        //clusterProgress(0);
        do {
            iterationCount++;
            
            m1Step();
            
            m2Step();
            
            //clusterProgress(iterationCount);
            
            eo = e;
            e = error();
        } while (Math.abs(eo - e) > ERROR_LIMIT);
    }
    
    /*
    private void clusterProgress(int it) {
        Dimension SIZE = new Dimension(400,400);
        JFrame frame = new JFrame();
        ClusterPanel cpanel = new ClusterPanel(getClusters(), getClusterCenter());
        frame.getContentPane().add(cpanel);
        //frame.setBackground(Color.white);
        frame.pack();
        frame.setSize(SIZE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width-SIZE.width)/2,(screenSize.height-SIZE.height)/2);        
        frame.show();
        
        try {
            ComponentPrinter printer = new ComponentGIFPrinter(cpanel);
            printer.print(new File("c:/temp/cluster/kmnDcluster.3." + Util.format(it, 2) + ".gif"));
        } catch (Exception e) {
            System.out.println("Histogram: " + e);
            e.printStackTrace();
        }  
        
        frame.setVisible(false);
    }
    */
    public DataPoint[][] getClusters() {
        int size = data.length;
        Vector[] clusterVec = new Vector[nclusters];
        for (int i = 0; i < nclusters; i++) {
            clusterVec[i] = new Vector(size/nclusters);
        }
        
        for (int i = 0; i < size; i++) {
            clusterVec[cluster[i]].add(data[i].clone());
        }
        
        DataPoint[][] dp = new DataPoint[nclusters][];
        for (int i = 0; i < nclusters; i++) {
            dp[i] = new DataPoint[clusterVec[i].size()];
            clusterVec[i].toArray(dp[i]);
        }
        
        return dp;
    }
    
    public DataPoint[] getClusterCenter() {
        DataPoint[] ret = new DataPoint[clusterCenter.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (DataPoint)clusterCenter[i].clone();
        }
        return ret;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Iterations: ").append(iterationCount).append("\n");
        for (int i = 0; i < nclusters; i++) {
            buffer.append(i).append(": ");
            buffer.append(clusterCenter[i].toString()).append("\n");
        }
        
        return buffer.toString();        
    }
}
