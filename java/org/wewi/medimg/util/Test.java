/*
 * Test.java
 *
 * Created on 4. April 2002, 19:39
 */

package org.wewi.medimg.util;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import org.wewi.medimg.seg.statistic.*;

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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        try {
            TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                     new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.read();
            Image image = reader.getImage();
            //ImageSegmentationStrategy strategy = new MLSegmentation(image, 4);
            ImageSegmentationStrategy strategy = new KMeansSegmentation(image, 4);
            
            //Timer timer = new Timer("ML");
            //timer.start();
            strategy.segmentate();
            ModelBasedSegmentation mbs = strategy.getModelBasedSegmentation();
            Timer timer = new Timer("ADSF");
            mbs.segmentate(image);
            //Image segimage = strategy.getSegmentedImage();

            timer.stop();
            timer.print();  
            
            
            //TIFFWriter writer = new TIFFWriter(segimage, new File("C:/Temp/seghead"));
            //writer.setColorConversion(new FeatureColorConversion());
            //writer.write();
            
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
}
