/*
 * MLSegmentation.java
 *
 * Created on 17. Januar 2002, 15:36
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.seg.ImageSegmentationStrategy;
import org.wewi.medimg.seg.SegmentationEvent;

import org.wewi.medimg.image.io.*;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.FeatureData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;

import org.wewi.medimg.util.Timer;

import java.util.Arrays;

import java.io.File;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class MLSegmentation extends ImageSegmentationStrategy {
    protected final static int MAX_ITER = 25;
    protected final static double ERROR_LIMIT = 0.001;
    
    protected int m1m2Count = 0;
    
    protected int nfeatures;
    protected double[] meanValues;
    protected double[] meanValuesOld;

    public MLSegmentation(Image image, int nf) {
        super(image);
        nfeatures = nf;
        featureData = null;

        meanValues = new double[nfeatures];
        meanValuesOld = new double[nfeatures];
        //Initialize the meanValues
        Arrays.fill(meanValues, 0d);
        Arrays.fill(meanValuesOld, 0d);     
    }   

    protected void initMeanValues() {
        int[] minMax = new int[2];//imageData.getColorRange();
        minMax[0] = 0; minMax[1] = 255;
        int colorDelta = (minMax[1]-minMax[0])/(nfeatures+1);
        for (int i = 0; i < nfeatures; i++) {
            meanValues[i] = minMax[0] + colorDelta*(i+1);
            meanValuesOld[i] = meanValues[i];
        }
    }

    protected double neighbourhoodWeight(int x, int y, int z, int f) {
        return 0;
    }

    protected boolean isM1Ready() {
        return true;
    }
        
    protected boolean isM1M2Ready(int m1m2Iteration) {
        double epsilonTemp = 0;
        boolean ready = false;
        for (int j = 0; j < nfeatures; j++) {
            epsilonTemp += Math.abs(meanValues[j] - meanValuesOld[j]);
        }
        if (epsilonTemp <= ERROR_LIMIT || m1m2Iteration > MAX_ITER) {
            ready = true;
            System.out.println("count: " + m1m2Iteration);
            
            //Für die Fortschrittsanzeige///////////////////////////////////////

            ////////////////////////////////////////////////////////////////////
        } else {
            //Für die Fortschrittsanzeige///////////////////////////////////////

            ////////////////////////////////////////////////////////////////////            
        }

        for (int j = 0; j < nfeatures; j++) {
            meanValuesOld[j] = meanValues[j];
        }
        return ready;
    }

    protected void initM1Iteration() {
    }

    protected double getMeanValue(int f, int x, int y, int z) {
        return meanValues[f];
    }

    private void m1Step() {
        int minX = image.getMinX();
        int minY = image.getMinY();
        int minZ = image.getMinZ();
        int maxX = image.getMaxX();
        int maxY = image.getMaxY();
        int maxZ = image.getMaxZ();
        int minFeatureIndex;
        int color;
        double nw;
        double minFeature, minFeatureTemp;

        initM1Iteration();
        int count = 0;
        do {
            count = 0;
            for (int i = minX; i <= maxX; i++) {
                for (int j = minY; j <= maxY; j++) {
                    for (int k = minZ; k <= maxZ; k++) {
                        color = image.getColor(i, j, k);
                        minFeature = Integer.MAX_VALUE;
                        minFeatureIndex = 0;
                        for (int l = 0; l < nfeatures; l++) {
                            nw = neighbourhoodWeight(i, j, k, l);
                            minFeatureTemp = Math.abs((double)color-getMeanValue(l, i, j, k));
                            minFeatureTemp += nw;

                            if (minFeatureTemp < minFeature) {
                                minFeatureIndex = l;
                                minFeature = minFeatureTemp;
                            }
                        }
                        featureData.setFeature(i, j, k, (byte)minFeatureIndex);
                    }
                }
            }
        } while (!isM1Ready());        
    }
    
    protected void m2Step() {
        long[] meanTemp = new long[nfeatures];
        int[] meanNoTemp = new int[nfeatures];
        Arrays.fill(meanTemp, 0);
        Arrays.fill(meanNoTemp, 0);

        byte feat;
        int npixel = image.getNVoxels();
        for (int i = 0; i < npixel; i++) {
            feat = featureData.getFeature(i);
            meanTemp[feat] += (long)image.getColor(i);
            meanNoTemp[feat]++;
        }

        for (int i = 0; i < nfeatures; i++) {
            if (meanNoTemp[i] == 0) {
                meanValues[i] = 1;
            } else {
                meanValues[i] = (double)meanTemp[i] / (double)meanNoTemp[i];
            }
        }
        Arrays.sort(meanValues);

        featureData.setMeanValues(meanValues);

        //Debuging/////////////////////////////////////////////////////////////
        SegmentationEvent state = new SegmentationEvent(this, m1m2Count, meanValues);
        System.out.println(state);
        ///////////////////////////////////////////////////////////////////////
      
    }
    
    public void doSegmentation() {
        featureData = new FeatureData(image.getMaxX()+1, image.getMaxY()+1, image.getMaxZ()+1, nfeatures);

        initMeanValues();
        notifySegmentationStarted(new SegmentationEvent(this, m1m2Count, meanValues));
        do {
            //Informieren der Observer; start einer neuen Iteration
            m1Step();
            m2Step();
            ++m1m2Count;
            
            notifyIterationFinished(new SegmentationEvent(this, m1m2Count, meanValues));
        } while(!isM1M2Ready(m1m2Count));

        ///Debugging////////////////////////////////////////////////////////////
        SegmentationEvent state = new SegmentationEvent(this, m1m2Count, meanValues);
        System.out.println(state);
        System.out.println("Varianz:");
        double[] variance = getVariance();
        for (int i = 0; i < variance.length; i++) {
            System.out.print("" + variance[i] + " : "); 
        }
        //////////////////////////////////////////////////////////////////////// 
        
        //Informieren der Observer, wenn die Segmentierung beendet ist
        notifySegmentationFinished(new SegmentationEvent(this, m1m2Count, meanValues));
    }
    

    public double[] getMeanValues() {
        double[] mv = new double[meanValues.length];
        System.arraycopy(meanValues, 0, mv, 0, mv.length);
        return mv;
    }
    
    public double[] getVariance() {
        double[] variance = new double[meanValues.length];
        int[] count = new int[meanValues.length];
        Arrays.fill(variance, 0);
        Arrays.fill(count, 0);
        
        int size = image.getNVoxels();
        byte f;
        int c;
        for (int i = 0; i < size; i++) {
            f = featureData.getFeature(i);
            c = image.getColor(i);
            variance[f] += Math.pow(meanValues[f]-c, 2);
            count[f]++;
        }
        for (int i = 0; i < count.length; i++) {
            variance[i] = variance[i]/(double)count[i];
        }
        
        return variance;
    }
    
    /**
     * Main- Methode für Testzwecke
     */
    public static void main(String[] args) {
        //File source = new File("C:/Workspace/fwilhelm/Projects/SRS/data/srsSEG/segTest/head.in.001");
        File source = new File("C:/Workspace/head");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), source);
        try {
            reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image = reader.getImage();
        System.out.println(image);
        
        TIFFWriter writer = new TIFFWriter(image, new File("C:/temp/head.out.out"));
        try {
            writer.write();
        } catch (Exception e) {
            System.out.println("adsf: " + e);
        }
        
        /*
        MLSegmentation segmenter = new MLSegmentation(image, 4);
        segmenter.doSegmentation();
        
        FeatureData data = segmenter.getFeatureData();
        
        RawImageWriter rwriter = new RawImageWriter(data, new File("C:/temp/image.raw"));
        try {
            rwriter.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(data);
        */
        
       /* 
        FeatureData featureData = segmenter.getFeatureData();
        try {
            featureData.writeObject(new File("c:/temp/fd.raw"));
        } catch (Exception e) {}
        TIFFWriter writer = new TIFFWriter(featureData, new File("C:/temp/head.out"));
        writer.write();
        */
    }
    
    
}




