/*
 * MCCVSegmentation.java
 *
 * Created on 2. Mai 2002, 15:34
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.QualityMeasure;

import org.wewi.medimg.math.GaussianDistribution;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.VoxelIterator;

import org.wewi.medimg.seg.ImageSegmentationStrategy;
import org.wewi.medimg.seg.ModelBasedSegmentation;
import org.wewi.medimg.seg.NullModelBasedSegmentation;

import cern.jet.random.engine.RandomEngine;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MCCVSegmentation extends ImageSegmentationStrategy {
    //Maximal number of features
    private final int K_MAX = 15;
    private final int K_MIN = 3;
    //Number of cross-validation runs
    private int M = 100;
    
    private int bestK;
    
    public MCCVSegmentation(Image image) {
        super(image);
    }
   
    /**
     * Starting the segmentation-procedure.
     */
    public void segmentate() {
        //Dataset for training
        VoxelIterator trainImageVoxelIterator;
        //Dataset for testing
        VoxelIterator testImageVoxelIterator;
        KMeansSegmentation kms;
        ModelBasedSegmentation mbkms;
        
        //Storing the average likelihood value for each k
        double[] averageLikelihood = new double[K_MAX-K_MIN+1];
        Arrays.fill(averageLikelihood, 0);
        
        
        MCCVStatisticWriter stat = new MCCVStatisticWriter("c:/Temp/mccv_1.stat");
        //Outer loop for the M ...
        for (int m = 0; m < M; m++) {
            //Generate train/test partitions
            trainImageVoxelIterator = new RandomPartitionVoxelIterator(image, 0.5, 1000*(m+1));
            testImageVoxelIterator = new RandomPartitionVoxelIterator(image, 0.5, 1001*(m+2));
            
            //Storing the likelihood values for each k
            double[] likelihood = new double[K_MAX-K_MIN+1];
            
            for (int k = K_MIN; k <= K_MAX; k++) {
                //Initialize the segmentation algorithm
                kms = new KMeansSegmentation(trainImageVoxelIterator, k);
                //run the segmentation algorithm with the training partition
                kms.segmentate();
                
                //apply the found model to the test partition and
                //calculate the log-likelihood (Equation 3 -- see paper)
                GaussianDistribution[] distribution = new GaussianDistribution[k];
                double[] mean = kms.getCenter();
                double[] variance = kms.getVariance();
                double[] pi = kms.getPi();               //a posteriori probability for the i-th feature
                for (int i = 0; i < k; i++) {
                    distribution[i] = new GaussianDistribution(mean[i], variance[i]);
                }
                MixtureModelDistribution mmd = new MixtureModelDistribution(distribution, pi);
                LogLikelihoodFunction llf = new LogLikelihoodFunction(testImageVoxelIterator, mmd);
                likelihood[k-K_MIN] = llf.eval();
                //System.out.println("asdf: " + likelihood[k]);
                averageLikelihood[k-K_MIN] = likelihood[k-K_MIN];
                
                System.out.println("M: " + m + ", K: " + k);
                
            }
            
            stat.write(m+1, K_MIN, averageLikelihood);
        }
        
        stat.close();
        
        for (int i = 0; i < K_MAX-K_MIN+1; i++) {
            System.out.println("" + (i+K_MIN) + " " + (averageLikelihood[i]/M));
        }
        
        //Average the cross-validated estimates for each k over all m.
        //Take the k with the highest estimates
        double maxVal = Double.MIN_VALUE;
        int maxK = 0;
        for (int i = K_MIN; i <= K_MAX; i++) {
            System.out.println("k: " + i + "   likelihood: " + averageLikelihood[i-K_MIN]);
            if (averageLikelihood[i-K_MIN] > maxVal) {
                maxVal = averageLikelihood[i-K_MIN];
                maxK = i;
            }
        }
        
        bestK = maxK;
        
        System.out.println("Bestes K: " + bestK);
    }    
    
    public Image getSegmentedImage() {
        return new NullImage();
    }
    
    public ModelBasedSegmentation getModelBasedSegmentation() {
        return new NullModelBasedSegmentation();
    }
    
    public QualityMeasure getQualityMeasure() {
        return new QualityMeasure() {
                        public double quality() {
                            return 0;
                        }
                    };
    }
    
}