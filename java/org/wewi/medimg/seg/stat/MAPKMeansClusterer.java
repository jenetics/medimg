/**
 * MAPKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:10
 */

package org.wewi.medimg.seg.stat;

import java.util.Properties;

import org.wewi.medimg.image.Image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPKMeansClusterer extends MLKMeansClusterer {
    public static final String SEGMENTER_NAME = "MAP-Kmeans-Clusterer";
    
    private double BETA = 0.35;
    private double BETA_SQRT2 = Math.sqrt(BETA);  
    
    private Image segimgOld;
    private int size;
    private int oldPos = -1;
    private int[] n6 = new int[6];
    private int[] n12 = new int[12];    
    

	/**
	 * @see org.wewi.medimg.seg.stat.MLKMeansClusterer#MLKMeansClusterer(int)
	 */
    public MAPKMeansClusterer(int k) {
        super(k);
        ERROR_LIMIT = 0.2;
    }
    
    public MAPKMeansClusterer(int k, double BETA) {
        this(k);
        this.BETA = BETA;
        BETA_SQRT2 = Math.sqrt(BETA); 
    }
    
    protected void setImageProperties(Image segimg) {
        Properties segProp = new Properties();
        segProp.setProperty("Segmentiermethode", getClass().getName());
        segProp.setProperty("k", Integer.toString(k));
        segProp.setProperty("BEAT", Double.toString(BETA));
        segProp.put("Iterationen", Integer.toString(iterationCount));
        for (int i = 0; i < k; i++) {
            segProp.setProperty("mean." + i, Double.toString(mean[i]));    
        }
        
        segimg.getHeader().setImageProperties(segProp);        
    }
    
	/**
	 * @see org.wewi.medimg.seg.stat.MLKMeansClusterer#createSegimgOld(Image)
	 */
    protected void createSegimgOld(Image segimg) {
        segimgOld = (Image)segimg.clone();
        size = segimgOld.getNVoxels();
    }
    
	/**
	 * @see org.wewi.medimg.seg.stat.MLKMeansClusterer#saveOldFeatureColor(int, int)
	 */
    protected void saveOldFeatureColor(int pos, int color) {
        segimgOld.setColor(pos, color);
    }
    
	/**
	 * @see org.wewi.medimg.seg.stat.MLKMeansClusterer#getCliquesPotential(int, int)
	 */
    protected double getCliquesPotential(int pos, int f) {
        double Vc = 0.0;

        if (oldPos != pos) {
            segimgOld.getNeighbor3D6Positions(pos, n6);
            segimgOld.getNeighbor3D12Positions(pos, n12);
            oldPos = pos;
        }
        
        for (int i = 0; i < 6; i++) {
            if (n6[i] >= 0 && n6[i] < size) {
                if (segimgOld.getColor(n6[i]) != f) {
                    Vc += BETA;
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            if (n12[i] >= 0 && n12[i] < size) {
                if (segimgOld.getColor(n12[i]) != f) {
                    Vc += BETA_SQRT2;
                }
            }
        }
        
        return Vc;
    }
    
    public void setBETA(double b) {
        BETA = b;
        BETA_SQRT2 = Math.sqrt(BETA);
    }
    
    public double getBETA() {
        return BETA;
    }
    
    public String getSegmenterName() {
        return SEGMENTER_NAME;    
    }
    
    public String toString() {
        return SEGMENTER_NAME + " (k:= " + k +")";    
    }
    
}
