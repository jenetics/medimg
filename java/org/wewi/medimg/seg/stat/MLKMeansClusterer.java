/*
 * MLKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:08
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ColorRange;

import org.wewi.medimg.seg.ObservableSegmenter;

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
public class MLKMeansClusterer extends ObservableSegmenter implements Clusterer {
    protected final static int MAX_ITERATION = 50;
    protected final static double ERROR_LIMIT = 0.001;
    
    protected final int k;
    protected double[] mean;
    protected double[] meanOld;
    

    public MLKMeansClusterer(int k) {
        this.k = k;
        mean = new double[k];
        meanOld = new double[k];
        Arrays.fill(mean, 0);
        Arrays.fill(meanOld, 0);
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
    
    protected void createSegimgOld(Image segimg) {
    }
    
    public Image segment(Image mrt) {
        Image segimg = (Image)mrt.clone();
        segimg.resetColor(0);
        segment(mrt, segimg);
        return segimg;
    }
    
    public void segment(Image mrt, Image segimg) {
        int iterationCount = 0;
        
        createSegimgOld(segimg);
        initMeans(mrt.getColorRange());
        do {
            m1Step(mrt, segimg);
            m2Step(mrt, segimg);
            iterationCount++;
        } while(ERROR_LIMIT < error() &&
                MAX_ITERATION >= iterationCount);        
    }
    
    private void m1Step(Image mrt, Image segimg) {
        int size = mrt.getNVoxels();
        int minDistanceFeature;
        int color;
        double cp, distance, minDistance;

        for (int i = 0; i < size; i++) {
            color = mrt.getColor(i);
            minDistance = Integer.MAX_VALUE;
            minDistanceFeature = 0;
            
            for (int f = 0; f < k; f++) {
                distance = mean[f] - (double)color;
                distance *= distance;
                cp = getCliquenPotential(i, f); 
                distance += cp;
                if (distance < minDistance) {
                    minDistanceFeature = f;
                    minDistance = distance;
                }
            } 
            
            saveOldFeatureColor(i, segimg.getColor(i));
            segimg.setColor(i, minDistanceFeature);
        }
                  
    }
    
    private void m2Step(Image mrt, Image segimg) {
        long[] meanSum = new long[k];
        int[] meanNo = new int[k];
        Arrays.fill(meanSum, 0);
        Arrays.fill(meanNo, 0);

        int f = 0;
        int size = mrt.getNVoxels();
        for (int i = 0; i < size; i++) {
            f = segimg.getColor(i);
            meanSum[f] += (long)mrt.getColor(i);
            meanNo[f]++;
        }
        
        System.arraycopy(mean, 0, meanOld, 0, mean.length);

        for (int i = 0; i < k; i++) {
            if (meanNo[i] == 0) {
                mean[i] = 0;
            } else {
                mean[i] = (double)meanSum[i] / (double)(meanNo[i]);
            }
        }
        Arrays.sort(mean);  
    }    
    
    protected double error() {
        double err = 0;
        for (int i = 0; i < k; i++) {
            err += Math.abs(mean[i] - meanOld[i]);
        }
        return err;
    }
    
    protected void saveOldFeatureColor(int pos, int color) {
    }
    
    protected double getCliquenPotential(int pos, int f) {
        return 0;
    }
    
    
    
    
    
    public static void main(String[] args) {
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.setRange(new Range(50,100));
            reader.read();
            Image mrt = reader.getImage();
            
            //Clusterer clusterer = new MLKMeansClusterer(4);
            Clusterer clusterer = new MAPKMeansClusterer(4);
            Image segimg = clusterer.segment(mrt);
            
            ImageWriter writer = new TIFFWriter(segimg, new File("C:/Temp/outhead"));
            writer.setColorConversion(new FeatureColorConversion());
            writer.write();
            
            
        } catch (Exception e) {
            System.out.println("TESTFEHLER: " + e);
            e.printStackTrace();
        }
        
    }
    
}
