/**
 * Created on 20.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.seg.validation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFWriter;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BrainWebDataConverter {

	/**
	 * Constructor for BrainWebDataConverter.
	 */
	public BrainWebDataConverter() {
		super();
	}
    
    public static void readBrain() {
        final int maxX = 181;
        final int maxY = 217;
        final int maxZ = 181;     
        
        
        String modality = "pd";   
        String noise = "9";
        
        //String input = "D:/msbrain." + modality + ".n" + noise + ".rf20.8bit.dat";
        //String output = "X:/msheads/" + modality + ".n" + noise + ".rf20";
        String input = "D:/msbrain.modell.seg.8bit.dat";
        String output = "X:/mshead/seg.model";
        Properties imageProp = new Properties();
        imageProp.setProperty("BrainType", "NORMAL.MODEL");
        //imageProp.setProperty("Modality", modality.toUpperCase());
        //imageProp.setProperty("Noise", noise + "%");
        //imageProp.setProperty("RF", "20%");
        imageProp.setProperty("URL", "CDATA[http://www.bic.mni.mcgill.ca/brainweb/]");
        
        try {
            //Einlesen des Bildes
            System.out.println("Einlesen des Bildes " + input);
            DataInputStream in = new DataInputStream(new FileInputStream(input));
            Image img = new ImageData(maxX, maxY, maxZ);
            int result = 0;
            for (int k = 0; k < maxZ; k++) {
                for (int j = 0; j < maxY; j++) {
                    for (int i = 0; i < maxX; i++) {
                        result = in.readUnsignedByte();
                        img.setColor(i, j, k, result);
                    }   
                }   
            }
            in.close();
            
            //Schreiben des Bildes
            System.out.println("Schreiben des Bildes " + output);
            img.getHeader().setImageProperties(imageProp);
            img.setColorConversion(new FeatureColorConversion());
            ImageWriter writer = new TIFFWriter(img, new File(output));
            writer.write();
            
        } catch (Exception e) {
            System.out.println("" + e); 
            e.printStackTrace();
        }
            
    }    

	public static void main(String[] args) {
        readBrain();
	}
}
