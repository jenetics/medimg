/*
 * KMeansSegmentation.java
 *
 * Created on 8. Mai 2002, 17:18
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.QualityMeasure;

import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;

import org.wewi.medimg.seg.ImageSegmentationStrategy;
import org.wewi.medimg.seg.SegmentationEvent;
import org.wewi.medimg.seg.ModelBasedSegmentation;
import org.wewi.medimg.seg.SimpleSegmentationImage;
import org.wewi.medimg.seg.FeatureImage;

import java.util.Arrays;

import cern.jet.random.engine.RandomEngine;
import cern.jet.random.engine.MersenneTwister;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class KMeansSegmentation extends ImageSegmentationStrategy {
    private static final int COLORS = 256;
    private static final double CENTER_EPSILON = 1;
    
    private VoxelIterator imageVoxelIterator;
    private final int k;
    
    private double[] center;
    private double[] variance;
    private double[] pi;  //a posteriori probability for the i-th feature
    private IntervalTree intervalTree;
    
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    
    public KMeansSegmentation(Image image, int k) {
        super(image);
        this.k = k;
        imageVoxelIterator = image.getVoxelIterator();
        init();
    }
    
    public KMeansSegmentation(VoxelIterator voxelIterator, int k) {
        this.k = k;
        imageVoxelIterator = voxelIterator;
        init();
    }
    
    private void init() {
        center = new double[k];
        pi = new double[k];
        RandomEngine random = new MersenneTwister((int)(System.currentTimeMillis()%Integer.MAX_VALUE));
        for (int i = 0; i < k; i++) {
            center[i] = random.nextDouble()*(COLORS-1);
        }
        Arrays.sort(center);
            
        intervalTree = new IntervalTree(center);
    }
    
    public double[] getCenter() {
        double[] c = new double[k];
        System.arraycopy(center, 0, c, 0, k);
        return c;
    }
    
    public double[] getPi() {
        double[] result = new double[k];
        System.arraycopy(pi, 0, result, 0, k);
        return result;
    }
    
    private void calculateVariance() {
        int[] count = new int[k];
        double[] sum = new double[k];
        variance = new double[k];
        int color = 0, feature = 0;
        double temp;
        for (VoxelIterator it = (VoxelIterator)imageVoxelIterator.clone(); it.hasNext();) {
            color = it.next();
            feature = intervalTree.intervalNumber(color); 
            temp = center[feature]-color;
            sum[feature] = temp*temp;
            ++count[feature];
        }
        for (int i = 0; i < k; i++) {
            variance[i] = sum[i];//(double)count[feature];
        }
    }
    
    /**
     * Lazy evaluation of the variance
     */
    public double[] getVariance() {
        if (variance == null) {
            calculateVariance();
        }
        double[] result = new double[k];
        System.arraycopy(variance, 0, result, 0, k);
        return result;
    }
    
    public void segmentate() {
        int[] colorCount = new int[k];
        long[] colorSum = new long[k];
        double[] centerTemp = new double[k];
        double epsilon;        
        
        notifySegmentationStarted(new SegmentationEvent(this));
        do {
            Arrays.fill(colorCount, 0);
            Arrays.fill(colorSum, 0);
            int feature = 0, color = 0;
            for (VoxelIterator it = (VoxelIterator)imageVoxelIterator.clone(); it.hasNext();) {
                color = it.next();
                feature = intervalTree.intervalNumber(color);
                colorSum[feature] += color;
                ++colorCount[feature];
            }
            for (int i = 0; i < k; i++) {
                centerTemp[i] = (double)colorSum[i] / (double)(colorCount[i]-1);
            }
            
            epsilon = 0;
            for (int i = 0; i < k; i++) {
                epsilon += Math.abs(centerTemp[i] - center[i]);
            }
            System.arraycopy(centerTemp, 0, center, 0, k);
            intervalTree = new IntervalTree(center);
            
////////////////////////////////////////////////////////////////////
StringBuffer buffer = new StringBuffer();
for (int i = 0; i < k; i++) {
    String number = Double.toString(center[i]);
    if (number.length() > 8) {
        number = number.substring(0, 8);
    }
    buffer.append("(").append(number).append(") ");
}    
System.out.println(buffer.toString());
///////////////////////////////////////////////////////////////////
            
            
            notifyIterationFinished(new SegmentationEvent(this));
        } while (epsilon > CENTER_EPSILON);
        
        //Calculation the a posterioi probability for the i-th feature
        int size = imageVoxelIterator.size();
        for (int i = 0; i < k; i++) {
            pi[i] = (double)colorCount[i] / (double)size;
        }
        
        notifySegmentationFinished(new SegmentationEvent(this));
    }
    
    public ModelBasedSegmentation getModelBasedSegmentation() {
        return new ModelBasedKMeansSegmentation(center);
    }
    
    public QualityMeasure getQualityMeasure() {
        return null;
    }
    
    public Image getSegmentedImage() {
        return new NullImage();
    }

}
