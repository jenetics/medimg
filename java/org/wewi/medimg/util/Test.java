/*
 * Test.java
 *
 * Created on 4. April 2002, 19:39
 */

package org.wewi.medimg.util;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.geom.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import org.wewi.medimg.seg.statistic.*;
import org.wewi.medimg.math.*;

import java.util.Properties;
import java.util.prefs.*;
import java.io.*;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Test {
    
    /** Creates a new instance of Test */
    public Test() {
    }
    
    public static void test1() {
        try {
            TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                     new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.setRange(new Range(0, 100));
            reader.read();
            Image image = reader.getImage();
            ImageSegmentationStrategy strategy = new MLSegmentation(image, 4);
            //ImageSegmentationStrategy strategy = new KMeansSegmentation(image, 6);
            
            //Timer timer = new Timer("ML");
            //timer.start();
            strategy.segmentate();
            //ModelBasedSegmentation mbs = strategy.getModelBasedSegmentation();
            Timer timer = new Timer("ADSF");
            //mbs.segmentate(image);
            //Image segimage = strategy.getSegmentedImage();

            timer.stop();
            timer.print();  
            
            
            //TIFFWriter writer = new TIFFWriter(image, new File("C:/Temp/seghead_6"));
            //writer.setColorConversion(new FeatureColorConversion());
            //writer.write();
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public static void test2() {
        try {
            TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                     new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            Range range = new Range(100, 109);
            //reader.setRange(range);
            reader.read();
            Image image = reader.getImage();
            ImageSegmentationStrategy strategy = new MCCVSegmentation(image);
            
            Timer timer = new Timer("MCCV8");
            timer.start();
            strategy.segmentate();
            timer.stop();
            timer.print();  
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public static void test3() {
        try {
            TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                     new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            Range range = new Range(100, 109);
            //reader.setRange(range);
            reader.read();
            Image image = reader.getImage();
           
            Histogram hist = new Histogram(image);
            hist.generate();            
            
            KMeansSegmentation strategy = new KMeansSegmentation(image, 7);
            strategy.segmentate();
            
            double[] center = strategy.getCenter();
            double[] variance = strategy.getVariance();
            double[] weight = strategy.getPi();
            GaussianDistribution[] dist = new GaussianDistribution[center.length];
            for (int i = 0; i < center.length; i++) {
                dist[i] = new GaussianDistribution(center[i], variance[i]);
            }
            MixtureModelDistribution mm = new MixtureModelDistribution(dist, weight);
            
            double error = 0;
            for (int i = 0; i < 256; i++) {
                error += Math.abs(mm.eval(i)-hist.getRelativeFrequency(i));
            }
            
            System.out.println("Error: " + error);
           
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }         
    }
    
    public static void test4() {
        try {
            TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
           new File("C:/temp/g.tif")); 
            
            reader.read();
            Image image = reader.getImage();
            Image target = (Image)image.clone();
            target.resetColor(0);
            
            double[] m = {-1,0,0,640,
                          0,1,0,0,
                          0,0,1,0};
            AffineTransform t = new AffineTransform(m);
            t.transform(image, target);
            
            TIFFWriter writer = new TIFFWriter(target, new File("C:/temp/target.tif"));
            writer.write();
                         
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       test4();
  
    }
    
}
