/*
 * Test.java
 *
 * Created on 28. März 2002, 16:06
 */

package org.wewi.medimg.reg;

import java.io.File;

import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.reg.pca.PCARegistration;
import org.wewi.medimg.util.Timer;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class Test {

    /** Creates new Test */
    public Test() {
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        Timer timer1 = new Timer("Test: Gesamt");
        timer1.start();       
        Timer timer3 = new Timer("Test: Image lesen");
        timer3.start();         
        File source1 = new File("E:/temp/img/aa001.tif");
        File source2 = new File("E:/temp/img/bb001.tif");                
        //File source1 = new File("D:/temp/circle004.tif");
        //File source2 = new File("D:/temp/circle005.tif");   
        //File source1 = new File("E:/temp/img/erg/erg01/");
        //File source2 = new File("E:/temp/img/erg/erg02/");        
        //File source1 = new File("E:/Daten/Dicoms/source/Dicoms/daten/bud/raw/batch5/");
        //File source2 = new File("E:/Daten/Dicoms/source/Dicoms/daten/bud/raw/batch7/");
        TIFFReader reader1 = new TIFFReader(ImageDataFactory.getInstance(), source1);
        TIFFReader reader2 = new TIFFReader(ImageDataFactory.getInstance(), source2);    
        FeatureColorConversion tcc = new FeatureColorConversion();
        //reader1.setColorConversion(tcc);
        //reader2.setColorConversion(tcc);
        try {
            reader1.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image data1 = (Image)reader1.getImage();
        data1.setColorConversion(tcc);
        System.out.println(data1);        
        try {
            reader2.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image data2 = (Image)reader2.getImage();
        System.out.println(data2);
        timer3.stop();
        timer3.print();

        //Hier beginnt der eigentliche Code für die Transformationsberechnung
        //Datencontainer für die benötigten Transformationsparameter;
        //System.out.println("Mist111");
        //Erzeugen des Registrierers
        WeightPointTransformationImportance myStrategy = new WeightPointTransformationImportance();
        //ImportanceStrategy myStrategy = new ImportanceStrategy();
        //FittnessStrategy myStrategy = new FittnessStrategy();
        BBAffinityMetric myMetric = new BBAffinityMetric();
        //ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
        myStrategy.setErrorLimit(0.2);
            /*myStrategy.setImportance(Tissue.VENTRICLE, 0.0);
            myStrategy.setImportance(Tissue.BONE, 0.0);
            myStrategy.setImportance(Tissue.FAT, 0.0);
            myStrategy.setImportance(Tissue.DEEP_TISSUE, 0.0);
            myStrategy.setImportance(Tissue.GREY_MATTER, 1.0);
            myStrategy.setImportance(Tissue.WHITE_MATTER, 0.0);
            myStrategy.setImportance(Tissue.SOFT_TISSUE, 0.0);
            myStrategy.setImportance(Tissue.ANGULAR_GYRUS, 0.0);*/
        PCARegistration strategy = new PCARegistration();
        strategy.setAffinityMetric(myMetric);
        
        //Registrate reg = new Registrate(strategy, param);
        //System.out.println("Mist222222");
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();
        AffineTransformation transformation;
        try {
            transformation = (AffineTransformation)strategy.registrate(data1, data2);
	        timer2.stop();
	        timer2.print();
	        Image show = (Image)transformation.transform(data1);
	        show.setColorConversion(tcc);
	        TIFFWriter rwriter = new TIFFWriter(show, new File("E:/temp/img/erg2"));   
	        rwriter.write();         
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }
        
        /*try {
	        TIFFWriter rwriter = new TIFFWriter(data1, new File("E:/temp/img/erg2"));   
	        rwriter.write();         
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }*/

        //param.setSourceImage(show);
        //param.setTargetImage(data2);
         
        /*for (int i = 0 ; i < 1; i++) {
        Registrator strategy2 = new MCWarpingRegStrategy();
        Registrate reg2 = new Registrate(strategy2, param);
        Timer timer4 = new Timer("Test: calculate MC");
        param.temp = i;
        timer4.start();

        try {
            reg2.calculate();
        } catch (RegistrationException re) {
            System.out.println("Mist");
        }
        timer4.stop();
        timer4.print();        
        ////////////////////////////////
        //System.out.println("Mist33333");        
        ImageData show2 = (ImageData)param.getTargetImage();
        TIFFWriter rwriter2 = new TIFFWriter(show2, new File("C:/temp/erg2" + i + ".tif"));
        //TIFFWriter rwriter2 = new TIFFWriter(show2, new File("E:/temp/img/erg2.tif"));
        //rwriter2.setColorConversion(tcc);
        try {
            rwriter2.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        param.setSourceImage(param.getTargetImage());
        param.setTargetImage(data2);
        }*/
        //System.out.println(show2);
        timer1.stop();
        timer1.print();
        System.out.println("FERTIG");
    }

}
