/*
 * MAPKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:10
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;


/**
 *
 * @author  Franz Wilhelmstötter
 */
public class MAPKMeansClusterer extends MLKMeansClusterer {
    private static double BETA = 0.35;
    private static double BETA_SQRT2 = Math.sqrt(BETA);  
    
    private Image segimgOld;
    private int size;
    private int oldPos = -1;
    private int[] n6 = new int[6];
    private int[] n12 = new int[12];    
    
    /** Creates a new instance of MAPKMeansClusterer */
    public MAPKMeansClusterer(int k) {
        super(k);
    }
    
    protected void createSegimgOld(Image segimg) {
        segimgOld = (Image)segimg.clone();
        size = segimgOld.getNVoxels();
    }
    
    protected void saveOldFeatureColor(int pos, int color) {
        segimgOld.setColor(pos, color);
    }
    
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
    
    public void setBeta(double b) {
        BETA = b;
        BETA_SQRT2 = Math.sqrt(BETA);
    }
    
    public double getBeta() {
        return BETA;
    }
    
}
