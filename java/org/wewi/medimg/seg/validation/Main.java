/**
 * Created on 19.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.seg.validation;

import java.io.File;


import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.RawImageWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.seg.Segmenter;
import org.wewi.medimg.seg.stat.MLKMeansClusterer;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Main {
    
    

	public static void main(String[] args) {
        Image oimg = new NullImage();
        Image mimg = new NullImage();
        Image simg = new NullImage();
        
        Segmenter seg = null;
        seg = new MLKMeansClusterer(10);
        
        try {
            System.out.println("Lesen des Modells");
            ImageReader reader = new RawImageReader(ImageDataFactory.getInstance(),
                                            new File("X:/images/nbrain.model.rid"));
            reader.read();
            mimg = reader.getImage();
            System.out.println("Lesen des Bildes");
            reader = new RawImageReader(ImageDataFactory.getInstance(),
                                            new File("X:/images/nbrain.rid"));
            reader.read();
            oimg = reader.getImage();
            
        } catch (Exception e) {
            e.printStackTrace(); 
            return;   
        }
        
        System.out.println("Segmentieren");
        simg = seg.segment(oimg);
        
        System.out.println("Validieren");
        Validator val = new Validator(simg, mimg);
        val.validate();
        
        System.out.println(val);
        
	}
}





