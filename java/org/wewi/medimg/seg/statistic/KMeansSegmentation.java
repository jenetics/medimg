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
import org.wewi.medimg.seg.LightSegmentationImage;
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
    private double[][] interval;
    
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
        RandomEngine random = new MersenneTwister((int)(System.currentTimeMillis()%Integer.MAX_VALUE));
        for (int i = 0; i < k; i++) {
            center[i] = random.nextDouble()*(COLORS-1);
        }
        Arrays.sort(center);
        
        interval = new double[k][2];
        calculateInterval();        
    }
    
    private void calculateInterval() {
        //Initialisieren der Intervalle; geht sicher noch schneller!
        for (int i = 1; i < k-1; i++) {
            interval[i][0] = (center[i-1]+center[i])/2;
            interval[i][1] = (center[i]+center[i+1])/2;
        }
        interval[0][0] = 0;
        interval[0][1] = interval[1][0];
        interval[k-1][1] = COLORS; // schiach
        interval[k-1][0] = interval[k-2][1];        
    }
    
    public double[] getCenter() {
        double[] c = new double[k];
        System.arraycopy(center, 0, c, 0, k);
        return c;
    }
    
    
    public void segmentate() {
        int size = image.getNVoxels();
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
                for (int i = 0; i < k; i++) {
                    if (color >= interval[i][0] && color < interval[i][1]) {
                        feature = i;
                        break;
                    }
                }

                colorSum[feature] += color;
                colorCount[feature]++;
            }
            for (int i = 0; i < k; i++) {
                centerTemp[i] = (double)colorSum[i] / (double)colorCount[i];
            }
            
            epsilon = 0;
            for (int i = 0; i < k; i++) {
                epsilon += Math.abs(centerTemp[i] - center[i]);
            }
            System.arraycopy(centerTemp, 0, center, 0, k);
            calculateInterval();
            
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
