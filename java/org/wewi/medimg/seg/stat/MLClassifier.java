/*
 * MLClassifier.java
 *
 * Created on 24. Juli 2002, 19:34
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;

import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.Classifier;

import org.wewi.medimg.util.Timer;

/**
 *
 * @author  Franz Wilhelmst�tter
 */
public class MLClassifier extends ObservableSegmenter implements Classifier {
    private double[] mean;
    private double[] var;
    private double[] deviation;
    
    /**
     * Dieser Maximum-Likelihood-Klassifikator geht
     * von Normalverteilten klassenbedingten
     * Dichteverteilungen aus.
     *
     * @param mean die Erwartungswerte der Normalverteilungen
     * @param var die Varianzen der Normalverteilungen
     */
    public MLClassifier(double[] mean, double[] var) {
        this.mean = new double[mean.length];
        this.var = new double[mean.length];
        this.deviation = new double[mean.length];
        System.arraycopy(mean, 0, this.mean, 0, mean.length);
        System.arraycopy(var, 0, this.var, 0, mean.length);
        
        for (int i = 0; i < mean.length; i++) {
            deviation[i] = Math.sqrt(var[i]);
        }
    }
    
    public Image segment(Image mrt) {
        Image segimg = (Image)mrt.clone();
        segimg.resetColor(0);
        segment(mrt, segimg);
        return segimg;
    }
    
    public void segment(Image mrt, Image segimg) {
        int size = mrt.getNVoxels();
        for (int i = 0; i < size; i++) {
            segimg.setColor(i, e(mrt.getColor(i)));
        }
    }
   
    private int e(int color) {
        double prop;
        double propMax = -Double.MAX_VALUE;
        int maxFeatureIndex = 0;
        
        for (int i = 0; i < mean.length; i++) {
            prop = p(color, i);
            if (prop > propMax) {
                maxFeatureIndex = i;
                propMax = prop;
            }
        }
        return maxFeatureIndex;
    }    
    
    private double p(double color, int f) {
        return Math.exp(-((color-mean[f])*(color-mean[f]))/(2*var[f])) /
                       deviation[f];
    }
     
}