/*
 * MCCVSegmentation.java
 *
 * Created on 2. Mai 2002, 15:34
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.image.Image;

import org.wewi.medimg.seg.ImageSegmentationStrategy;

import cern.jet.random.engine.RandomEngine;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MCCVSegmentation extends ImageSegmentationStrategy {
    //Maximal number of features
    private int K_MAX = 5;
    //Number of cross-validation runs
    private int M = 10;
    
    
    public MCCVSegmentation(Image image) {
        super(image);
    }
   
    /**
     * Starting the segmentation-procedure.
     */
    public void doSegmentation() {
    }    
    
}