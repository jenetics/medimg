/*
 * Validator.java
 *
 * Created on 7. August 2002, 15:32
 */

package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.io.*;
import org.wewi.medimg.image.*;
import java.io.*;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Validator {
    
    /** Creates a new instance of Validator */
    public Validator() {
    }
    
    //public double get
    
    public static void main(String[] args) {
        //Image img = new ImageData(181, 217, 167);
        
        try { 
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), new File("../../data/head"));
            reader.read();
            Image img = reader.getImage();
            
            ImageWriter writer = new RawImageWriter(img, new File("../../data/head.dat"));
            writer.setColorConversion(new FeatureColorConversion());
            writer.write();
        } catch (Exception e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
        
    }
    
}
