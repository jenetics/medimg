/*
 * MLKMeansEstimator.java
 *
 * Created on 24. Juli 2002, 19:36
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ColorRange;

import org.wewi.medimg.seg.Segmenter;
import org.wewi.medimg.seg.Estimator;

import java.util.Arrays;
import java.util.Random;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import java.io.*;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class MLKMeansEstimator implements Estimator {
    private final static int MAX_ITERATION = 50;
    private final static double ERROR_LIMIT = 0.001;
    
    private Image mrt;
    private final int k;
    private double[] mean;
    private double[] meanOld; 
    private double[] var;
    
    private double[] colorSum;
    private double[] colorSqrSum;
    private double[] colorNo;
    
    /** Creates a new instance of MLKMeansEstimator */
    public MLKMeansEstimator(Image mrt, int k) {
        this.mrt = mrt;
        this.k = k;
        mean = new double[k];
        meanOld = new double[k];
        var = new double[k];
        Arrays.fill(mean, 0);
        Arrays.fill(meanOld, 0);
        
        colorSum = new double[k];
        colorSqrSum = new double[k];
        colorNo = new double[k];
    }
    
    protected void initMeans(ColorRange cr) {
        Random random = new Random(System.currentTimeMillis());
        
        double range = cr.getMaxColor()-cr.getMinColor();
        for (int i = 0; i < k; i++) {
            mean[i] = (random.nextDouble()*range) + (double)cr.getMinColor();
            meanOld[i] = mean[i];
        }
        Arrays.sort(mean);  
        Arrays.sort(meanOld);        
    }    
    
    public void estimate() {
        int iterationCount = 0;
        
        initMeans(mrt.getColorRange());
        do { 
            m1Step(mrt);
            m2Step();
            iterationCount++;
        } while(ERROR_LIMIT < error() &&
                MAX_ITERATION >= iterationCount);    
        calculateVarinace();
        
        System.out.println("ITCOUNT: " + iterationCount);
        for (int i = 0; i < k; i++) {
            System.out.println("mean[" + i + "]:= " + mean[i]);
        }
        for (int i = 0; i < k; i++) {
            System.out.println("var[" + i + "]:= " + var[i]);
        }        
    }
    
    private void m1Step(Image mrt) {
        int size = mrt.getNVoxels();
        int minDistanceFeature;
        int color;
        double cp, distance, minDistance;

        
        Arrays.fill(colorSum, 0);
        Arrays.fill(colorSqrSum, 0);
        Arrays.fill(colorNo, 0);         
        for (int i = 0; i < size; i++) {
            color = mrt.getColor(i);
            minDistance = Double.MAX_VALUE;
            minDistanceFeature = 0;
            
            for (int f = 0; f < k; f++) {
                distance = mean[f] - (double)color;
                distance *= distance;
                if (distance < minDistance) {
                    minDistanceFeature = f;
                    minDistance = distance;
                }
            } 

            colorSum[minDistanceFeature] += (double)color;
            colorSqrSum[minDistanceFeature] += (double)(color*color);
            colorNo[minDistanceFeature]++;
        }            
    }  
    
    private void m2Step() {
        System.arraycopy(mean, 0, meanOld, 0, mean.length);

        for (int i = 0; i < k; i++) {
            if (colorNo[i] == 0) {
                mean[i] = 0;
            } else {
                mean[i] = (double)colorSum[i] / (double)(colorNo[i]);
            }
        }
        Arrays.sort(mean);  
    }  
    
    private double error() {
        double err = 0;
        for (int i = 0; i < k; i++) {
            err += Math.abs(mean[i] - meanOld[i]);
        }
        return err;
    }   
    
    private void calculateVarinace() {
        for (int i = 0; i < k; i++) {
            if (colorNo[i] > 1) {
                var[i] = (colorSqrSum[i]-2.0*mean[i]*colorSum[i]+
                          colorNo[i]*mean[i]*mean[i])/
                               (colorNo[i]-1.0);
            } else {
                var[i] = 1;
            }
        }
    }
    
    public Segmenter getSegmenter() {
        return new MLClassifier(mean, var);
    }
    
    
    public static void main(String[] args) {
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.setRange(new Range(50,100));
            reader.read();
            Image mrt = reader.getImage();
            
            Estimator estimator = new MLKMeansEstimator(mrt, 7);
            estimator.estimate();
            Segmenter classifier = estimator.getSegmenter();
            Image segimg = classifier.segment(mrt);
            
            ImageWriter writer = new TIFFWriter(segimg, new File("C:/Temp/outhead"));
            writer.setColorConversion(new FeatureColorConversion());
            writer.write();
            
            
        } catch (Exception e) {
            System.out.println("TESTFEHLER: " + e);
            e.printStackTrace();
        }
        
    }    
    
}
