/*
 * ModelBasedKMeansSegmentation.java
 *
 * Created on 10. Mai 2002, 14:15
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.seg.ModelBasedSegmentation;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ModelBasedKMeansSegmentation implements ModelBasedSegmentation {
    private double[] center;
    private double[][] interval;
    private final int k;
    private static final int COLORS = 256;
    
    /** Creates a new instance of ModelBasedKMeansSegmentation */
    public ModelBasedKMeansSegmentation(double[] center) {
        k = center.length;
        this.center = new double[k];
        System.arraycopy(center, 0, this.center, 0, k);
        Arrays.sort(this.center);
        
        interval = new double[k][2];
        calculateInterval();
    }
    
    private void calculateInterval() {
        //Initialisieren der Intervalle; geht sicher noch schneller!
        interval = new double[k][2];
        for (int i = 1; i < k-1; i++) {
            interval[i][0] = (center[i-1]+center[i])/2;
            interval[i][1] = (center[i]+center[i+1])/2;
        }
        interval[0][0] = 0;
        interval[0][1] = interval[1][0];
        interval[k-1][1] = COLORS; // schiach
        interval[k-1][0] = interval[k-2][1];        
    }    
    
    public void segmentate(Image source, Image target) {
        int size = source.getNVoxels();
        int color;
        for (int i = 0; i < size; i++) {
            color = source.getColor(i);
            for (int j = 0; j < k; j++) {
                if (color >= interval[j][0] && color < interval[j][1]) {
                    target.setColor(i, j);
                    break;
                }
            }
        }
    }
    
    public void segmentate(Image sourceTarget) {
        int size = sourceTarget.getNVoxels();
        int color;
        for (int i = 0; i < size; i++) {
            color = sourceTarget.getColor(i);
            for (int j = 0; j < k; j++) {
                if (color >= interval[j][0] && color < interval[j][1]) {
                    sourceTarget.setColor(i, j);
                    break;
                }
            }
        }        
    }
    
}
