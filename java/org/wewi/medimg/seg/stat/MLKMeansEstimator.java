/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * MLKMeansEstimator.java
 *
 * Created on 24. Juli 2002, 19:36
 */

package org.wewi.medimg.seg.stat;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.seg.Estimator;
import org.wewi.medimg.seg.Segmenter;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
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

        initMeans(AnalyzerUtils.getColorRange(mrt));
        
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
            ImageReader reader = new TIFFReader(IntImageFactory.getInstance(),
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.read();
            Image mrt = reader.getImage();
            
            Estimator estimator = new MLKMeansEstimator(mrt, 7);
            estimator.estimate();
            Segmenter classifier = estimator.getSegmenter();
            Image segimg = classifier.segment(mrt);
            segimg.setColorConversion(new FeatureColorConversion());            
            
            ImageWriter writer = new TIFFWriter(segimg, new File("C:/Temp/outhead"));
            writer.write();
            
            
        } catch (Exception e) {
            System.out.println("TESTFEHLER: " + e);
            e.printStackTrace();
        }
        
    }    
    
}
