/*
 * MLClassifier.java
 *
 * Created on 24. Juli 2002, 19:34
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;

import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.Classifier;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class MLClassifier extends ObservableSegmenter implements Classifier {
    
    /**
     * Dieser Maximum-Likelihood-Klassifikator geht
     * von Normalverteilten klassenbedingten
     * Dichteverteilungen aus.
     *
     * @param mean die Erwartungswerte der Normalverteilungen
     * @param var die Varianzen der Normalverteilungen
     */
    public MLClassifier(double[] mean, double[] var) {
    }
    
    public Image segment(Image image) {
        Image feature = (Image)image.clone();
        segment(image, feature);
        return feature;
    }
    
    public void segment(Image image, Image feature) {
    }
    
}
