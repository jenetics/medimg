/**
 * Test.java
 *
 * Created on 28. März 2002, 16:06
 */

package org.wewi.medimg.reg;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFrame;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.ImageTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.CrossCorrelationOperator;
import org.wewi.medimg.image.ops.DirectComparisonOperator;
import org.wewi.medimg.image.ops.MutualInformationOperator;
import org.wewi.medimg.image.ops.NormalizedMutualInformationOperator;
import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;
import org.wewi.medimg.reg.pca.NonRigidPCARegistration;
import org.wewi.medimg.reg.pca.RigidPCARegistration;
import org.wewi.medimg.util.Timer;
import org.wewi.medimg.viewer.ImageViewerSynchronizer;
import org.wewi.medimg.viewer.VectorFieldPanel;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.image.ImagePanel;
import org.wewi.medimg.viewer.image.ImageViewer;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class Test {

private static String path = "E:/Daten/Diplom/data/reg.test.img/";
private static String result;
private static String storePath;


    /** Creates new Test */
    public Test() {
    }
    

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        File[] suite1 = new File[2];
        suite1[0] = new File(path + "serie01_04_a.tif");
        suite1[1] = new File(path + "serie01_04_b.tif");
        

        
        //File[] suite1 = new File[2];
        //suite1[0] = new File(path + "try002.tif");
        //suite1[1] = new File(path + "try001.tif");
        //suite1[0] = new File(path + "/set3/");
        //suite1[1] = new File(path + "/set2/");
        /*
        File[] suite1 = new File[18];
        suite1[0] = new File(path + "serie01_01_a.tif");
        suite1[1] = new File(path + "serie01_01_b.tif");
        suite1[2] = new File(path + "serie01_02_a.tif");
        suite1[3] = new File(path + "serie01_02_b.tif");
        suite1[4] = new File(path + "serie01_03_a.tif");
        suite1[5] = new File(path + "serie01_03_b.tif");   
        suite1[6] = new File(path + "serie01_04_a.tif");
        suite1[7] = new File(path + "serie01_04_b.tif");
        suite1[8] = new File(path + "serie01_05_a.tif");
        suite1[9] = new File(path + "serie01_05_b.tif");
        suite1[10] = new File(path + "serie01_06_a.tif");
        suite1[11] = new File(path + "serie01_06_b.tif"); 
        suite1[12] = new File(path + "serie01_07_a.tif");
        suite1[13] = new File(path + "serie01_07_b.tif"); 
        suite1[14] = new File(path + "serie01_08_a.tif");
        suite1[15] = new File(path + "serie01_08_b.tif");
        suite1[16] = new File(path + "serie01_09_a.tif");
        suite1[17] = new File(path + "serie01_09_b.tif");
        
        File[] suite2 = new File[14];
        suite2[0] = new File(path + "serie02_01_a.tif");
        suite2[1] = new File(path + "serie02_01_b.tif");
        suite2[2] = new File(path + "serie02_02_a.tif");
        suite2[3] = new File(path + "serie02_02_b.tif");
        suite2[4] = new File(path + "serie02_03_a.tif");
        suite2[5] = new File(path + "serie02_03_b.tif");   
        suite2[6] = new File(path + "serie02_04_a.tif");
        suite2[7] = new File(path + "serie02_04_b.tif");
        suite2[8] = new File(path + "serie02_05_a.tif");
        suite2[9] = new File(path + "serie02_05_b.tif");
        suite2[10] = new File(path + "serie02_06_a.tif");
        suite2[11] = new File(path + "serie02_06_b.tif"); 
        suite2[12] = new File(path + "serie02_07_a.tif");
        suite2[13] = new File(path + "serie02_07_b.tif");         
        
        File[] suite3 = new File[12];
        suite3[0] = new File(path + "serie03_01_a.tif");
        suite3[1] = new File(path + "serie03_01_b.tif");
        suite3[2] = new File(path + "serie03_02_a.tif");
        suite3[3] = new File(path + "serie03_02_b.tif");
        suite3[4] = new File(path + "serie03_03_a.tif");
        suite3[5] = new File(path + "serie03_03_b.tif");   
        suite3[6] = new File(path + "serie03_04_a.tif");
        suite3[7] = new File(path + "serie03_04_b.tif");
        suite3[8] = new File(path + "serie03_05_a.tif");
        suite3[9] = new File(path + "serie03_05_b.tif");
        suite3[10] = new File(path + "serie03_06_a.tif");
        suite3[11] = new File(path + "serie03_06_b.tif");
        
        
        File[] suite4 = new File[12];
        suite4[0] = new File(path + "serie04_01_a.tif");
        suite4[1] = new File(path + "serie04_01_b.tif");
        suite4[2] = new File(path + "serie04_02_a.tif");
        suite4[3] = new File(path + "serie04_02_b.tif");
        suite4[4] = new File(path + "serie04_03_a.tif");
        suite4[5] = new File(path + "serie04_03_b.tif");   
        suite4[6] = new File(path + "serie04_04_a.tif");
        suite4[7] = new File(path + "serie04_04_b.tif");
        suite4[8] = new File(path + "serie04_05_a.tif");
        suite4[9] = new File(path + "serie04_05_b.tif");
        suite4[10] = new File(path + "serie04_06_a.tif");
        suite4[11] = new File(path + "serie04_06_b.tif"); 
       
        File[] suite5 = new File[12];
        suite5[0] = new File(path + "serie05_01_a.tif");
        suite5[1] = new File(path + "serie05_01_b.tif");
        suite5[2] = new File(path + "serie05_02_a.tif");
        suite5[3] = new File(path + "serie05_02_b.tif");
        suite5[4] = new File(path + "serie05_03_a.tif");
        suite5[5] = new File(path + "serie05_03_b.tif");   
        suite5[6] = new File(path + "serie05_04_a.tif");
        suite5[7] = new File(path + "serie05_04_b.tif");
        suite5[8] = new File(path + "serie05_05_a.tif");
        suite5[9] = new File(path + "serie05_05_b.tif");
        suite5[10] = new File(path + "serie05_06_a.tif");
        suite5[11] = new File(path + "serie05_06_b.tif"); 
        
                
        File[] suite6 = new File[2];
        suite6[0] = new File(path + "erg/");
        suite6[1] = new File(path + "erg2/");
        */
        
        String store = "test";
        storePath = path + store + "/";
        testSuite(suite1);
        /*
                 store = "suite5";
        storePath = path + store + "/";
        testSuite(suite5);
        */
        /*
                 store = "suite5";
        storePath = path + store + "/";
        testSuite(suite5); */    


        //transform
        /*
        TIFFReader reader1 = new TIFFReader(ImageDataFactory.getInstance(), path);
        FeatureColorConversion tcc = new FeatureColorConversion();
        reader1.setColorConversion(tcc);
        Image data1;
        try {
            reader1.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("gelesen");
        data1 = (Image)reader1.getImage();
        data1.setColorConversion(tcc);
        System.out.println(data1.getDimension());
        AffineTransformation at = AffineTransformation.getRotateInstance(0, 0,  Math.PI/2.0);
        ImageTransformation transformation = null;
        Image show;     
        try {
                    
                    ImageDataFactory fac = ImageDataFactory.getInstance();
                    show = at.transform(data1, fac);
                    System.out.println(show.getDimension());
                    System.out.println("transformiert");
                    TIFFWriter rwriter = new TIFFWriter(show, new File(path + "head1."));   
                    rwriter.write();
                    //rwriter.writeView(data1.getDimension(), 0); 
                    System.out.println("fertig");
       
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        } 
        */

   

    }
          
    private static void testSuite(File[] images) {
        Timer timer1 = new Timer("Test: Gesamt");
        timer1.start();       
        File source1;
        File source2; 
        Image data1;
        Image data2;          
        //for (int i = 0, n = images.length/2; i < n; i++) {
        for (int i = 1, n = 1 + images.length/2; i < n; i++) {
            
            Timer timer3 = new Timer("Test: Image lesen");
            timer3.start(); 
        
            source1 = images[2*(i-1)];
            source2 = images[2*(i-1)+1];
            TIFFReader reader1 = new TIFFReader(IntImageFactory.getInstance(), source1);
            TIFFReader reader2 = new TIFFReader(IntImageFactory.getInstance(), source2);    
            FeatureColorConversion tcc = new FeatureColorConversion();
            reader1.setColorConversion(tcc);
            reader2.setColorConversion(tcc);
            try {
                reader1.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
            data1 = (Image)reader1.getImage();
            data1.setColorConversion(tcc);
            try {
                reader2.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
            data2 = (Image)reader2.getImage();
            timer3.stop();
            timer3.print(); 
            validateImages(data1, data2, i, "im Original:", null);
            validateImages(data2, data1, i, "im Original verkehrt:", null);
            //testPCA(data1, data2, i);
            testAffinPCA(data1, data2, i); 
            //testMCWarping(data1, data2, i);    
            //testCompleteMethod(data1, data2, i);       
        }    
        timer1.stop();
        timer1.print();
        System.out.println("FERTIG");
        System.out.println(result);    
    
    }
    
    
    
    private static void validateImages(Image source, Image target, int number,
                                  String message, Transformation trans) {
        ColorRange cr1 = AnalyzerUtils.getColorRange(target);
        ColorRange cr2 = AnalyzerUtils.getColorRange(source);
        
        BBAffinityMetric myMetric = new BBAffinityMetric();
        result += "Uebereinstimmung der Bilder " + number + " " + message + "\n";
        result += "BB: " + myMetric.similarity(source, target, trans) + "\n";
        
        MutualInformationOperator op = new MutualInformationOperator(cr1, cr2);
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(target, source, op);
        analyzer.analyze();
        result += "MI: " + op.getMutualInformation() + "\n";
        NormalizedMutualInformationOperator op2 = new NormalizedMutualInformationOperator(cr1, cr2);
        analyzer = new BinaryPointAnalyzer(target, source, op2);
        analyzer.analyze();
        result += "NMI: " + op2.getMutualInformation() + "\n";
        DirectComparisonOperator op3 = new DirectComparisonOperator(cr1, cr2);
        analyzer = new BinaryPointAnalyzer(target, source, op3);
        analyzer.analyze();
        result += "DC: " + op3.getDirectComparison() + "\n";
        CrossCorrelationOperator op4 = new CrossCorrelationOperator(cr1, cr2);
        analyzer = new BinaryPointAnalyzer(target, source, op4);
        analyzer.analyze();
        result += "CC: " + op4.getCrossCorrelation() + "\n";
    }
    
    
    private static void testPCA(Image source, Image target, int count) {
        //WeightPointTransformationImportance myStrategy = new WeightPointTransformationImportance();
        //ImportanceStrategy myImportance = new ImportanceStrategy();
        FittnessTransformationImportance myImportance = new FittnessTransformationImportance();
        //ManualTransformationImportance myImportance = new ManualTransformationImportance();
        BBAffinityMetric myMetric = new BBAffinityMetric();
        //ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
        myImportance.setErrorLimit(0.2);
        RigidPCARegistration strategy = new RigidPCARegistration(); 
        strategy.setAffinityMetric(myMetric);
        strategy.setTransformationImportance(myImportance);
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();
        ImageTransformation transformation = null;
        Image show;     
        try {
                    transformation = (ImageTransformation)strategy.registrate(source, target);
                    timer2.stop();
                    timer2.print();
                    Timer timer3 = new Timer("Test: rPCA: transform");
                    timer3.start();
			        IntImageFactory fac = IntImageFactory.getInstance();
                    show = transformation.transform(source, fac);
                    timer3.stop();
                    timer3.print();
                    Timer timer4 = new Timer("Test: rPCA: write");
                    timer4.start();
                    TIFFWriter rwriter = new TIFFWriter(show, new File(storePath + "image" + count + "/rigidPCA."));   
                    rwriter.writeView(source.getDimension(), 0); 
                    validateImages(target, show, count, "mit rigider PCA:", null);
                    timer4.stop();
                    timer4.print();
        
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }           
           
    }
    
    private static void testAffinPCA(Image source, Image target, int count) {
        //WeightPointTransformationImportance myImportance = new WeightPointTransformationImportance();
        //ManualTransformationImportance myImportance = new ManualTransformationImportance();
        FittnessTransformationImportance myImportance = new FittnessTransformationImportance();
        //ManualTransformationImportance myImportance = new ManualTransformationImportance();
        BBAffinityMetric myMetric = new BBAffinityMetric();
        //ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
        myImportance.setErrorLimit(0.2);
        /*myImportance.setImportance(1, 1.0);
        myImportance.setImportance(9, 0.0);
        myImportance.setImportance(10, 0.0);
        myImportance.setImportance(11, 0.0);
        */
        NonRigidPCARegistration strategy = new NonRigidPCARegistration();
        strategy.setAffinityMetric(myMetric);
        strategy.setTransformationImportance(myImportance);
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();
        ImageTransformation transformation = null;
        Image show = null;     
        try {
                    transformation = (ImageTransformation)strategy.registrate(source, target);
                    timer2.stop();
                    timer2.print();
                    Timer timer3 = new Timer("Test: aPCA: transform");
                    timer3.start();
			        IntImageFactory fac = IntImageFactory.getInstance();
                    show = transformation.transform(source, fac);
                    timer3.stop();
                    timer3.print();
                    Timer timer4 = new Timer("Test: aPCA: write");
                    timer4.start();
                    //show = transformation.transform(source);
                    validateImages(target, show, count, "mit affiner PCA:", null);
                    TIFFWriter rwriter = new TIFFWriter(show, new File(storePath + "image" + count + "/affinPCA."));   
                    rwriter.writeView(source.getDimension(), 0); 
                    timer4.stop();
                    timer4.print();
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }           
           
    }    
    
    private static void testMCWarping(Image source, Image target, int count) {
        //WeightPointTransformationImportance myStrategy = new WeightPointTransformationImportance();
        //ImportanceStrategy myImportance = new ImportanceStrategy();
        FittnessTransformationImportance myImportance = new FittnessTransformationImportance();
        //ManualTransformationImportance myImportance = new ManualTransformationImportance();
        BBAffinityMetric myMetric = new BBAffinityMetric();
        //ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
        myImportance.setErrorLimit(0.2);
        MonteCarloWarping strategy = new MonteCarloWarping();
        strategy.setAffinityMetric(myMetric);
        strategy.setTransformationImportance(myImportance);
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();
        ImageTransformation transformation = null;
        Image show = null;     
        try {
                    transformation = (ImageTransformation)strategy.registrate(source, target);
                    timer2.stop();
                    timer2.print();
                    Timer timer3 = new Timer("Test: MC: transform");
                    timer3.start();
			        IntImageFactory fac = IntImageFactory.getInstance();
                    show = transformation.transform(source, fac);
                    timer3.stop();
                    timer3.print();
                    Timer timer4 = new Timer("Test: MC: write");
                    timer4.start();
                    validateImages(target, show, count, "mit Monte Carlo Warping:", null);
                    TIFFWriter rwriter = new TIFFWriter(show, new File(storePath + "image" + count + "/MCWarping."));   
                    //rwriter.write();  
                    rwriter.writeView(source.getDimension(), 0);
                    timer4.stop();
                    timer4.print();
                    /*JFrame frame = new JFrame();
                    frame.getContentPane().add(new VectorFieldPanel((VectorField)transformation, show.getDimension()));
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                            System.exit(0);
                        }
                    });
                   
                    frame.setSize(show.getDimension().getSizeX(), show.getDimension().getSizeY());
                    frame.show();*/
                    
                    
                    String out;
                    out = "" + (VectorField)transformation;
                    FileWriter file = new FileWriter(new File(storePath + "image" + count + "/MCWarping/Vectorfield.txt"));
                     
                    //toString von VectorField 
                    
                    double[] start = new double[3];
                    double[] end = new double[3];
                    double[] p1 = new double[2];
                    double[] p2 = new double[2];  
                    out = out + "\n{";                                    
                    for (VectorIterator it = ((VectorField)transformation).getVectorIterator(); it.hasNext();) {
                        it.next(start, end);
                        if (it.hasNext()) {
                            out = out + "{{" + start[0] + "," + start[1] + "," + start[2] + "},{" 
                                      + (end[0] - start[0]) + "," + (end[1] - start[1]) + "," + (end[2] - start[2]) + "}},\n"; 
                        } else {
                            out = out + "{{" + start[0] + "," + start[1] + "," + start[2] + "},{" 
                                      + (end[0] - start[0]) + "," + (end[1] - start[1]) + "," + (end[2] - start[2]) + "}}}\n"; 
                        }
                    }
                    file.write(out);
                    file.close();
                    
                    
                                        
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }           
           
    } 
    
    private static void testCompleteMethod(Image source, Image target, int count) {
        


        
        //WeightPointTransformationImportance myStrategy = new WeightPointTransformationImportance();
        //ImportanceStrategy myImportance = new ImportanceStrategy();
        FittnessTransformationImportance myImportance = new FittnessTransformationImportance();
        //ManualTransformationImportance myImportance = new ManualTransformationImportance();
        BBAffinityMetric myMetric = new BBAffinityMetric();
        //ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
        myImportance.setErrorLimit(0.2);
            /*myImportance.setImportance(0, 0.0);
            myImportance.setImportance(1, 0.0);
            myImportance.setImportance(4, 1.0);
            myImportance.setImportance(5, 0.0);
            myImportance.setImportance(6, 0.0);
            myImportance.setImportance(8, 0.0);
            myImportance.setImportance(9, 0.0);
            myImportance.setImportance(10, 0.0);
            myImportance.setImportance(11, 0.0); */ 
        NonRigidPCARegistration strategy = new NonRigidPCARegistration();          
        MonteCarloWarping strategy2 = new MonteCarloWarping();
        strategy.setAffinityMetric(myMetric);
        strategy.setTransformationImportance(myImportance);
        strategy2.setAffinityMetric(myMetric);
        strategy2.setTransformationImportance(myImportance);
        Timer timer2 = new Timer("Test: calculate");
        timer2.start();
        ImageTransformation transformation = null;
        ImageTransformation transformation2 = null;
        Image show = null;   
        Image show2 = null;           
        try {
                    transformation = (ImageTransformation)strategy.registrate(source, target);
			        IntImageFactory fac = IntImageFactory.getInstance();
                    timer2.stop();
                    Timer timer3 = new Timer("Test: PCM: transform");
                    timer3.start();
                    show = transformation.transform(source, fac);
                    timer3.stop();
                    timer2.start();
                    transformation2 = (ImageTransformation)strategy2.registrate(show, target);
                    timer2.stop();
                    timer2.print();
                    timer3.start();
                    //show2 = transformation2.transform(show, fac);
                    show2 = transformation2.transform(show, fac);
                    timer3.stop();
                    timer3.print();
                    Timer timer4 = new Timer("Test: rPCA: write");
                    timer4.start();
                    /*JFrame frame = new JFrame();
                    frame.getContentPane().add(new VectorFieldPanel((VectorField)transformation2, show2.getDimension()));
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                            System.exit(0);
                        }
                    });
                   
                    frame.setSize(show.getDimension().getSizeX(), show.getDimension().getSizeY());
                    frame.show();*/

                    validateImages(target, show2, count, "mit kombiniertem PCA und Monte Carlo Warping:", null);
                    TIFFWriter rwriter = new TIFFWriter(show2, new File(storePath + "image" + count + "/PCAMCWarping."));   
                    rwriter.writeView(source.getDimension(), 0);
                    //rwriter.write();
                    timer4.stop();
                    timer4.print(); 
                    String out;
                    out = "" + (VectorField)transformation2;
                    FileWriter file = new FileWriter(new File(storePath + "image" + count + "/PCAMCWarping/Vectorfield.txt"));
 
                    
                    //toString von VectorField 
                    
                    double[] start = new double[3];
                    double[] end = new double[3];
                    double[] p1 = new double[2];
                    double[] p2 = new double[2];  
                    out = out + "\n{";                                    
                    for (VectorIterator it = ((VectorField)transformation2).getVectorIterator(); it.hasNext();) {
                        it.next(start, end);
                        if (it.hasNext()) {
                            out = out + "{{" + start[0] + "," + start[1] + "},{" 
                                      + (end[0] - start[0]) + "," + (end[1] - start[1]) + "}},\n"; 
                        } else {
                            out = out + "{{" + start[0] + "," + start[1] + "},{" 
                                      + (end[0] - start[0]) + "," + (end[1] - start[1]) + "}}}\n"; 
                        }
                    }
                    file.write(out);
                    file.close();
                    
 
        } catch (Exception re) {
            System.out.println("Mist");
            re.printStackTrace();
        }           
           
    }     
        



}
