/*
 * MAPSegmentation.java
 *
 * Created on 26. Jänner 2002, 11:53
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.image.Image;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class MAPSegmentation extends MLSegmentation {
    private static int M1_ITERATIONS;
    private static double BETA = 0.35;
    private static double BETA_SQRT2 = Math.sqrt(BETA);

    private int m1Iteration;
    private int nvoxels;
    private int oldPos = -1;
    private int[] n6 = null;
    private int[] n12 = null;
    
    private byte[][] featureTest6 = new byte[nfeatures][6];
    private byte[][] featureTest12 = new byte[nfeatures][12];

    
    public MAPSegmentation(Image image, int nf, int m1It) {
        super(image, nf);
        M1_ITERATIONS = m1It;
        nvoxels = image.getNVoxels();
        
        for (int i = 0; i < nfeatures; i++) {
            Arrays.fill(featureTest6[i], (byte)i);
            Arrays.fill(featureTest12[i], (byte)i);
        }
    }


    protected boolean isM1Ready() {
        return !(++m1Iteration <= M1_ITERATIONS);
    }

    protected double neighbourhoodWeight(int pos, int f) {    
        double V = 0.0;

        if (oldPos != pos) {
            n6 = featureImage.getNeighbor3D6Positions(pos);
            n12 = featureImage.getNeighbor3D12Positions(pos);
            oldPos = pos;
        }
        
        //if (!Arrays.equals(n6, featureTest6[f])) {
        for (int i = 0; i < 6; i++) {
            if (n6[i] >= 0 && n6[i] < nvoxels) {
                if (featureImage.getOldFeature(pos) != f) {
                    V += BETA;
                }
            }
        }
        //}
        //if (!Arrays.equals(n12, featureTest12[f])) {
        for (int i = 0; i < 12; i++) {
            if (n12[i] >= 0 && n12[i] < nvoxels) {
                if (featureImage.getOldFeature(pos) != f) {
                    V += BETA_SQRT2;
                }
            }
        }
        //}
        
        return V;
    }

    protected void initM1Iteration() {
        m1Iteration = 0;
    }

}
