/**
 * Test.java
 *
 * Created on 4. April 2002, 19:39
 */

package org.wewi.medimg.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.RGBColorConversion;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.JPEGWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;


/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Test {
    
    /** Creates a new instance of Test */
    public Test() {
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
            //AffineTransformation t = new AffineTransformation(m);
            //t.transform(image, target);
            
            TIFFWriter writer = new TIFFWriter(target, new File("C:/temp/target.tif"));
            writer.write();
                         
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public static void test5() {
    	Image img = new ImageData(181, 218, 181);
    	Timer timer = new Timer("SetTest");
    	
        timer.start();
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                for (int k = 0; k < 150; k++) {
                    img.setColor(i, j, k, i);			
                }	
        	}	
        }
        timer.stop();
        timer.print();  
        
        timer.start();
        for (int i = img.getMinX(); i <= img.getMaxX(); i++) {
            for (int j = img.getMinY(); j <= img.getMaxY(); j++) {
                for (int k = img.getMinZ(); k <= img.getMaxZ(); k++) {
                    img.getColor(i, j, k);           
                }   
            }   
        }
        timer.stop();
        timer.print(); 
        
        timer.start();
        int size = img.getNVoxels();
        for (int i = 0; i < size; i++) {
            img.getColor(i);    
        }  
        timer.stop();
        timer.print();   
        
        
        timer.start();
        for (VoxelIterator it = img.getVoxelIterator(); it.hasNext();) {
            it.next();
        }  
        timer.stop();
        timer.print();
        
        VoxelIterator it = img.getVoxelIterator();
        timer.start();
        while (it.hasNext()) {
            it.next();    
        }
        timer.stop();
        timer.print();
        

        
         
    	
    }
    
    public static void test6() {
        ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                             new File("X:/t1.n3.rf20"));
        
        try {
            reader.read();
        } catch (Exception e) {
            System.out.println("" + e);
            e.printStackTrace();    
        }
        
        Image image = reader.getImage();
        
        ImageWriter writer = new JPEGWriter(image, new File("X:/t1.n3.rf20.jpg"));
        
        try {
            writer.write();
        } catch (Exception e) {
            System.out.println("" + e);
            e.printStackTrace();    
        }        
        
    }
    
    
    public static void test7() {
        SAXBuilder builder = new SAXBuilder();
        try {
			Document doc = builder.build(new URL("http://www.entwickler.com/"));
            System.out.println(doc);
		} catch (MalformedURLException e) {
            System.out.println("Fehler: " + e);
		} catch (JDOMException e) {
            System.out.println("Fehler: " + e);
		}
           
    }
    
    public static void test8() {
        Image img = new ImageData(381, 318, 381);
        ColorConversion cc = new RGBColorConversion();//img.getColorConversion();
        Timer timer = new Timer("SetTest");
        
        timer.start();
        int[] pixel = new int[3];
        for (int i = 0, n = img.getNVoxels(); i < n; i++) {
            cc.convert(img.getColor(i), pixel);    
        }
        timer.stop();
        timer.print();         
    }
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test8();
    }
    
}





















