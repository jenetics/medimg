/*
 * Validator.java
 *
 * Created on 7. August 2002, 15:32
 */

package org.wewi.medimg.seg.validation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.PseudoColorConversion;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Validator {
    private Image segimg;
    private Image compimg;
    
    
    /** Creates a new instance of Validator */
    public Validator(Image segimg, Image compimg) throws IllegalArgumentException {
    	if (!segimg.getDimension().equals(compimg.getDimension())) {
    		throw new IllegalArgumentException("Different Dimensions: " +
    		                                     segimg.getDimension().toString() + " != " +
    		                                     compimg.getDimension().toString());
    	}
    	
    	this.segimg = segimg;
    	this.compimg = compimg;
    }
    
    public void validate() {
    }
    
    public double getError() {
    	return 0;	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	public static void readBrain() {
		try {
			DataInputStream in = new DataInputStream(
			                           new FileInputStream(
			         "../../data/msbrain.pd.n9.rf20.1byte.dat"));
			Image img = new ImageData(181, 217, 181);
			
			int low = 0, high = 0, result = 0;
			for (int k = 0; k < 181; k++) {
				for (int j = 0; j < 217; j++) {
					for (int i = 0; i < 181; i++) {
						high = in.readUnsignedByte();
						//low = in.readUnsignedByte();
						//result = high*256;
						//result *= 256;
						//result += low;
						img.setColor(i, j, k, high);
					}	
				}	
			}
			in.close();
			
			System.out.println(img.getColorRange());
			//img.setColorConversion(new PseudoColorConversion());
			
			//ImageWriter writer = new RawImageWriter(img, new File("c:/temp/out.dat"));
			ImageWriter writer = new TIFFWriter(img, new File("c:/temp/msbrain.pd.n9.rf20.256c"));
			//img.setColorConversion(new FeatureColorConversion());
			writer.write();
			
		} catch (Exception e) {
			System.out.println("" + e);	
			e.printStackTrace();
		}
			
	}
    
    public static void main(String[] args) {
        //Image img = new ImageData(181, 217, 167);
        /*
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
        }*/
        
        Validator.readBrain();
        
    }
    
}
