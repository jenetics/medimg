/*
 * GaussianSegmentationModel.java
 *
 * Created on 6. Mai 2002, 11:01
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.math.GaussianDistribution;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.seg.ModelBasedSegmentation;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
final class GaussianModel implements ModelBasedSegmentation {
    private GaussianDistribution[] featureDistribution;
    private int nfeatures;
    private double[] mean;
    private double[][] interval;
    
    /** Creates a new instance of GaussianSegmentationModel */
    public GaussianModel(GaussianDistribution[] fd) {
        featureDistribution = fd;
        nfeatures = fd.length;
        
        mean = new double[nfeatures];
        for (int i = 0; i < nfeatures; i++) {
            mean[i] = featureDistribution[i].getMeanValue();
        }
        
        interval = new double[nfeatures][2];
        for (int i = 1; i < nfeatures-1; i++) {
            interval[i][0] = (mean[i-1]+mean[i])/2;
            interval[i][1] = (mean[i]+mean[i+1])/2;
        }
        interval[0][0] = 0;
        interval[0][1] = interval[1][0];
        interval[nfeatures-1][1] = 255; //maximal 255 grauwerte; schiach
        interval[nfeatures-1][0] = interval[nfeatures-2][1];
    }
    
    public void segmentate(Image source, Image target) {
        int size = source.getNVoxels();
        int color;
        for (int i = 0; i < size; i++) {
            color = source.getColor(i);
            for (int j = 0; j < interval.length; j++) {
                if (color >= interval[j][0] && color < interval[j][1]) {
                    target.setColor(i, j);
                    break;
                }
            }
        }
    }
    
}