/*
 * MCCVSegmentation.java
 *
 * Created on 2. Mai 2002, 15:34
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.QualityMeasure;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

import org.wewi.medimg.seg.ImageSegmentationStrategy;
import org.wewi.medimg.seg.ModelBasedSegmentation;

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
    public void segmentate() {
        VoxelIterator trainImage;
        VoxelIterator testImage;
        
        for (int m = 0; m < M; m++) {
            //Generate train/test partitions
            trainImage = new RandomPartitionVoxelIterator(image, 0.3, 1000*(m+1));
            testImage = new RandomPartitionVoxelIterator(image, 0.5, 1001*(m+2));
            for (int k = 1; k <= K_MAX; k++) {
                //Initialize the segmentation algorithm
                //run the segmentation algorithm with the training partition
                
                //apply the found model to the test partition and
                //calculate the log-likelihood (Equation 3 -- seee paper)
            }
        }
        
        //Average the cross-validated estimates for each k over all m.
        //Take the k with the highest estimates
        
    }    
    
    public Image getSegmentedImage() {
        return null;
    }
    
    public ModelBasedSegmentation getModelBasedSegmentation() {
        return null;
    }
    
    public QualityMeasure getQualityMeasure() {
        return null;
    }
    
}