/*
 * Test.java
 *
 * Created on 28. März 2002, 16:06
 */

package org.wewi.medimg.reg;

import java.io.File;

import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.TissueColorConversion;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.reg.interpolation.WeightPointStrategy;
import org.wewi.medimg.reg.metric.BBAffinityMetric;
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
        File source1 = new File("E:/temp/img/try07.tif");
        File source2 = new File("E:/temp/img/try08.tif");   
        //File source1 = new File("E:/Daten/Dicoms/source/Dicoms/daten/bud/raw/batch5/");
        //File source2 = new File("E:/Daten/Dicoms/source/Dicoms/daten/bud/raw/batch7/");
        TIFFReader reader1 = new TIFFReader(ImageDataFactory.getInstance(), source1);
        TIFFReader reader2 = new TIFFReader(ImageDataFactory.getInstance(), source2);    
        TissueColorConversion tcc = new TissueColorConversion();
        reader1.setColorConversion(tcc);
        reader2.setColorConversion(tcc);
        try {
            reader1.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageData data1 = (ImageData)reader1.getImage();
        System.out.println(data1);        
        try {
            reader2.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageData data2 = (ImageData)reader2.getImage();
        System.out.println(data2);
        timer3.stop();
        timer3.print();

        //Hier beginnt der eigentliche Code für die Transformationsberechnung
        //Datencontainer für die benötigten Transformationsparameter;
        RegisterParameter param = new RegisterParameter();
        param.setSourceImage(data1);
        param.setTargetImage(data2);
        //System.out.println("Mist111");
        //Erzeugen des Registrierers
        WeightPointStrategy myStrategy = new WeightPointStrategy();
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
        RegStrategy strategy = new PCARegStrategy(myStrategy, myMetric);

        Registrate reg = new Registrate(strategy, param);
        //System.out.println("Mist222222");
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();

        try {
            reg.calculate();
        } catch (RegistrationException re) {
            System.out.println("Mist");
        }
        timer2.stop();
        timer2.print();
        ////////////////////////////////
        //System.out.println("Mist33333");        
        ImageData show = (ImageData)param.getTargetImage();
        TIFFWriter rwriter = new TIFFWriter(show, new File("E:/temp/img/erg.tif"));
        rwriter.setColorConversion(tcc);
        try {
            rwriter.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(show);
        timer1.stop();
        timer1.print();
        System.out.println("FERTIG");
    }

}
