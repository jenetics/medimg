/**
 * Test.java
 *
 * Created on 4. April 2002, 19:39
 */

package org.wewi.medimg.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.ComplexAmplitudeImage;
import org.wewi.medimg.image.ComplexImage;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.RGBColorConversion;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.filter.DilationFilter;
import org.wewi.medimg.image.filter.EdgeFilter;
import org.wewi.medimg.image.filter.ErosionFilter;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.Kernel;
import org.wewi.medimg.image.filter.LinearNormalizeFilter;
import org.wewi.medimg.image.filter.TresholdFilter;
import org.wewi.medimg.image.filter.UnaryPointTransformerFilter;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.JPEGReader;
import org.wewi.medimg.image.io.JPEGWriter;
import org.wewi.medimg.image.io.PNGReader;
import org.wewi.medimg.image.io.PNGWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.ops.BinaryPointTransformer;
import org.wewi.medimg.image.ops.LinearNormalizeFunction;
import org.wewi.medimg.image.ops.MinMaxFunction;
import org.wewi.medimg.image.ops.RandomFunction;
import org.wewi.medimg.image.ops.SubFunction;
import org.wewi.medimg.image.ops.UnaryFunction;
import org.wewi.medimg.image.ops.UnaryPointTransformer;
import org.wewi.medimg.image.ops.UnaryPointTransformerFactory;
import org.wewi.medimg.math.GridVectorField;
import org.wewi.medimg.math.GridVectorFieldTransformer;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.MaxVectorLengthOperator;
import org.wewi.medimg.math.ScaleVectorFunction;
import org.wewi.medimg.math.VectorFieldAnalyzer;
import org.wewi.medimg.math.VectorFieldImageCanvasAdapter;
import org.wewi.medimg.math.fft.ImageDFT;
import org.wewi.medimg.seg.ac.GVFIntegral;
import org.wewi.medimg.seg.ac.GradientVectorFlow;


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
    
    public static void test9() {
        
        ImageReader reader = new JPEGReader(ImageDataFactory.getInstance(),
                                           new File("C:/Workspace/kappa/pic_242_0.jpg")); 
                                           
        try {
            reader.read();
        } catch (ImageIOException e) {
            System.out.println("MarginImage: " + e); 
            return;
        }    
        
        Image image = reader.getImage();
        
        Kernel h = Kernel.SOBEL_HORIZONTAL;
        Kernel v = Kernel.SOBEL_VERTICAL;
        EdgeFilter filter = new EdgeFilter(image, h, v);
        //ConvolutionFilter filter = new ConvolutionFilter(image, v);
        UnaryPointTransformerFilter norm = new UnaryPointTransformerFilter(filter, 
                                               new UnaryPointTransformerFactory(
                                                    new MinMaxFunction(0, 255)));
        norm.filter();
        
        ImageWriter writer = new TIFFWriter(image, new File("X:/margin.image.tif"));
        try {
            writer.write();
        } catch (ImageIOException e) {
            System.out.println("Fehler.test9: " + e);
        }        
    }
    
    public static void test10() {
        try {
            
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                                 new File("C:/Workspace/kappa/test.05.50x50.tif"));
            reader.read();
            Image image = reader.getImage();
            /*JFrame frame = new JFrame("HelloWorldSwing");
            ImagePanel ip = new ImagePanel(image);
            frame.getContentPane().add(ip);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.show();
            frame.repaint();
            ip.repaintImage();*/
            
            
            /*
            GradientFilter filter = new GradientFilter(image);
            filter.filter();
            VectorField field = filter.getGradientVectorField();
            System.out.println(field);
            */
            
            ImageFilter dilation = new DilationFilter((Image)image.clone());
            dilation.filter();
            ImageFilter erosion = new ErosionFilter(image);
            erosion.filter();
            
            BinaryPointTransformer sub = new BinaryPointTransformer(dilation.getImage(),
                                                                     erosion.getImage(),
                                                                     new SubFunction());
            sub.transform();
            Image edgeMap = sub.getImage();            
            
            GradientVectorFlow flow = new GradientVectorFlow(edgeMap);
            flow.start();
            GridVectorField field = (GridVectorField)flow.getGradientVectorField();
            
            MaxVectorLengthOperator op = new MaxVectorLengthOperator(); 
            VectorFieldAnalyzer analyzer = new VectorFieldAnalyzer(field, op);
            analyzer.analyze();
            double scale = 1d/op.getMaxLength(); 
            GridVectorFieldTransformer trans = new GridVectorFieldTransformer(field, 
                                               new ScaleVectorFunction(scale)); 
            trans.transform();            
            
            VectorFieldImageCanvasAdapter adapter = new VectorFieldImageCanvasAdapter(field);
            JFrame frame2 = new JFrame("HelloWorldSwing");
            ImagePanel ip2 = new ImagePanel(image);
            ip2.setImageCanvas(adapter);
            frame2.getContentPane().add(ip2);

            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setSize(300, 300);
            frame2.pack();
            frame2.setVisible(true);
            frame2.show();
            frame2.repaint();
            ip2.repaintImage();            
            
            /*
            ImageData id = new ImageData(image.getDimension());
            double[] p = new double[3];
            for (int i = 0; i <= id.getMaxX(); i++) {
                for (int j = 0; j <= id.getMaxY(); j++) {
                    field.getVector(i, j, 0, p);
                    id.setColor(i, j, 0, (int)(256*Math.sqrt(MathUtil.sqr(p[0])+MathUtil.sqr(p[1]))));        
                }    
            }*/
            
            GVFIntegral integral = new GVFIntegral(field);
            integral.calculate();
            Image img = integral.getImage();
            ImageFilter normal = new LinearNormalizeFilter(img, 0, 255);
            normal.filter();
            System.out.println(normal);
            
            ImageWriter writer = new TIFFWriter(normal.getImage(), new File("X:/out.tiff"));
            writer.write();
            
            
            
            
        } catch (Exception e) {
            System.out.println("Fehler test10");
            e.printStackTrace();    
        }    
    }
    
    public static void test11() {         
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                           new File("C:/Workspace/kappa/kappa.3.tif"));
            reader.read();
            Image image = reader.getImage();
            
            Image b = new ImageData(3, 3, 1);
            for (int i = 0; i < b.getNVoxels(); i++) {
                b.setColor(i, 0);    
            }
            
            /*
            ImageFilter op = new EdgeFilter(image, Kernel.SOBEL_HORIZONTAL, Kernel.SOBEL_VERTICAL);
            op = new TresholdFilter(op, 150, 255);
            //op = new DilationFilter(op, b);
            op = new LinearNormalizeFilter(op, 0, 255);
            //op = new ErosionFilter(op, b);
            op.filter();
            */
            
            ImageFilter dilation = new DilationFilter((Image)image.clone(), b);
            dilation.filter();
            ImageFilter erosion = new ErosionFilter(image, b);
            erosion.filter();
            
            BinaryPointTransformer sub = new BinaryPointTransformer(dilation.getImage(),
                                                                     erosion.getImage(),
                                                                     new SubFunction());
            sub.transform();
            
            /*
            image = sub.getImage();
            
            ImageFilter op = new ErosionFilter(image, b);
            //erosion = new ErosionFilter(erosion, b);
            //erosion = new ErosionFilter(erosion, b);
            //erosion = new ErosionFilter(erosion, b);
            //erosion.filter();
            
            op = new TresholdFilter(op, 40, 250);
            op = new LinearNormalizeFilter(op, 0, 255);
            op.filter();
            */
            ImageWriter writer = new TIFFWriter(sub.getImage(), new File("C:/Workspace/kappa/test11.erg"));
            writer.write();            
            
            
        } catch (Exception e) {
            System.out.println("MarginImage: " + e); 
            e.printStackTrace();
            return;
        }        
    }
    
    public static void test12() {
        Runtime run = Runtime.getRuntime();
        long mem = run.freeMemory();
        
        System.out.println("Freier Speicher: " + mem); 
        
        Image image;
        try {
            image = new ImageData(1000, 1000, 1000);
        } catch (OutOfMemoryError e) {
            System.out.println("Zu wenig freier Speicher");    
        } 
        
        System.out.println("Freier Speicher: " + run.freeMemory());  
    }
    
    
    public static void test13() {
        Image image = new ImageData(20, 32, 21); 
        image.resetColor(32);
        
        UnaryPointTransformer transformer = new UnaryPointTransformer(image, new RandomFunction());
        transformer.transform();
        
        
        try {
            ImageWriter writer = new PNGWriter(image, new File("X:/temp/random.png"));
            writer.write();
        } catch (Exception e) {
            System.out.println("Fehler: " + e);    
        }
                
    }
    
    public static void test14() {
        try {
            ImageReader reader = new PNGReader(ImageDataFactory.getInstance(),
                                                 new File("C:/Image1.png"));
            reader.read();
            Image image = reader.getImage();
            
            ImageDFT dft = new ImageDFT();
            ComplexImage cimage = dft.transform(image);
            
            Image absimage = new ComplexAmplitudeImage(cimage);
            ImageFilter filter = new LinearNormalizeFilter(absimage, 0, 255);
            filter.filter();
            
            ImageWriter writer = new PNGWriter(filter.getImage(), "X:/complex");
            writer.write();
            
            
        } catch (Exception e) {
            System.err.println("Fehler test14; " + e); 
            e.printStackTrace();   
        }    
    }
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test14();
    }
    
}





















