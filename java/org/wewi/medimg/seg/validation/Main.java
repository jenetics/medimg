/**
 * Created on 19.08.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;


import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.stat.MLKMeansClusterer;

/**
 * @author Franz Wilhelmstötter
 *
 */
public final class Main {
    
    

	public static void main(String[] args) {
        Image oimg = new NullImage();
        Image mimg = new NullImage();
        Image simg = new NullImage();
        
        ObservableSegmenter seg = null;
        seg = new MLKMeansClusterer(5);
        //seg.addLoggerHandler(new ConsoleHandler());
        
        try {
            System.out.println("Lesen des Modells");
            ImageReader reader = new RawImageReader(ImageDataFactory.getInstance(),
                                            new File("X:/images/nbrain.model.rid"));
            //reader.read();
            mimg = reader.getImage();
            System.out.println("Lesen des Bildes");
            reader = new RawImageReader(ImageDataFactory.getInstance(),
                                            new File("X:/images/nbrain.t1.n3.rf20.rid"));
            reader.read();
            oimg = reader.getImage();
            
        } catch (Exception e) {
            e.printStackTrace(); 
            return;   
        }
        
        System.out.println("Segmentieren");
        simg = seg.segment(oimg);
        System.out.println(simg.getColorRange());
        
        try {
            simg.setColorConversion(new FeatureColorConversion());
            ImageWriter writer = new TIFFWriter(simg, new File("X:/images/segimg"));
            writer.write();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        
        
        System.out.println("Validieren");
        //Validator val = new Validator(simg, mimg);
        //val.validate();
        
        //System.out.println(val);
        
	}
}





