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


import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageWriter;

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
        final String input = "../../data/nbrain.t1.n3.rf20.1byte.dat";
        final String output = "X:/images/nbrain.t1.n3.rf20.1byte.rid";
        
        final int maxX = 181;
        final int maxY = 217;
        final int maxZ = 181;
        
        
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(input));
            Image img = new ImageData(maxX, maxY, maxZ);
            
            int low = 0, high = 0, result = 0;
            for (int k = 0; k < maxZ; k++) {
                for (int j = 0; j < maxY; j++) {
                    for (int i = 0; i < maxX; i++) {
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
            
            ImageWriter writer = new RawImageWriter(img, new File(output));
            //ImageWriter writer = new TIFFWriter(img, new File("c:/temp/msbrain.pd.n9.rf20.256c"));
            //img.setColorConversion(new FeatureColorConversion());
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
