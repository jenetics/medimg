/**
 * Test.java
 *
 * Created on 4. April 2002, 19:39
 */

package org.wewi.medimg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.ComplexAmplitudeImage;
import org.wewi.medimg.image.ComplexImage;
import org.wewi.medimg.image.ComplexIndexImage;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.RGBColorConversion;
import org.wewi.medimg.image.filter.DilationFilter;
import org.wewi.medimg.image.filter.EdgeFilter;
import org.wewi.medimg.image.filter.ErosionFilter;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.Kernel;
import org.wewi.medimg.image.filter.LinearNormalizeFilter;
import org.wewi.medimg.image.filter.UnaryPointTransformerFilter;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.FieldFactory;
import org.wewi.medimg.image.geom.transform.GlobalInterpolator;
import org.wewi.medimg.image.geom.transform.RegularDisplacementField;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.JPEGReader;
import org.wewi.medimg.image.io.JPEGWriter;
import org.wewi.medimg.image.io.PNGReader;
import org.wewi.medimg.image.io.PNGWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointTransformer;
import org.wewi.medimg.image.ops.MinMaxFunction;
import org.wewi.medimg.image.ops.RandomFunction;
import org.wewi.medimg.image.ops.SubFunction;
import org.wewi.medimg.image.ops.UnaryPointTransformer;
import org.wewi.medimg.image.ops.UnaryPointTransformerFactory;
import org.wewi.medimg.image.statistic.SecondOrder;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.fft.ImageDFT;
import org.wewi.medimg.math.fft.NaiveDFT1D;
import org.wewi.medimg.math.geom.Dimension2D;
import org.wewi.medimg.math.vec.GridVectorField;
import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.ops.GridVectorFieldTransformer;
import org.wewi.medimg.math.vec.ops.MaxVectorLengthOperator;
import org.wewi.medimg.math.vec.ops.ScaleVectorFunction;
import org.wewi.medimg.math.vec.ops.VectorFieldAnalyzer;
import org.wewi.medimg.seg.ac.GVFIntegral;
import org.wewi.medimg.seg.ac.GradientVectorFlow;
import org.wewi.medimg.viewer.VectorFieldImageCanvasAdapter;
import org.wewi.medimg.viewer.VectorFieldPanel;
import org.wewi.medimg.viewer.image.ImagePanel;


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
        /*
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
        */

        
         
    	
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
                                                 new File("C:/Image2.png"));
            reader.read();
            Image image = reader.getImage();
            
            ImageDFT dft = new ImageDFT(new NaiveDFT1D());
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
    
    
    private static class FileExtentionFilter implements FilenameFilter {
        private String extention;
        
        public FileExtentionFilter(String extention) {
            this.extention = extention.toLowerCase();    
        }
        
        public boolean accept(File dir, String name) {
            if (dir.isDirectory()) {
                return true;    
            }
            return name.toLowerCase().endsWith(extention);
        }

    }
    private static List fileList(File dir) {
        List list = new ArrayList();
        
        File[] files = dir.listFiles(new Test.FileExtentionFilter(".jpg"));
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                list.addAll(fileList(files[i]));    
            } else {
                list.add(files[i]);    
            }    
        }
        
        return list;    
    }
    
    private static void copy(File from, File to) throws Exception {
        FileInputStream in = new FileInputStream(from);
        FileOutputStream out = new FileOutputStream(to);
        
        byte[] buffer = new byte[1024];
        int size = 0;
        while((size = in.read(buffer)) != -1) {
            out.write(buffer, 0, size);
        }
        
        in.close();
        out.close();
    }
    
    public static void test15() {
        String dir = "X:/x"; 
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(4);      
        
        List list = fileList(new File(dir));
        
        int count = 0;
        try {
            for (Iterator it = list.iterator(); it.hasNext();) {
                File file = (File)it.next();
                copy(file, new File(dir + "/" + "image." + format.format(count) + ".jpg"));
                //System.out.println(file);
                count++;   
            }
        } catch (Exception e) {
            e.printStackTrace();    
        } 
    }
    
    
    public static void test16() {
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                    "C:/Workspace/fwilhelm/Projekte/Diplom/code/data/nhead/seg.model");
            reader.read();
            Image img = reader.getImage();
            
            ImageDFT dft = new ImageDFT();
            ComplexImage cimg = dft.transform(img);
            
            ComplexIndexImage ciimg = new ComplexIndexImage(cimg,new Dimension2D(7, 10, -2.8, 2.8)); 
            
            LinearNormalizeFilter filter = new LinearNormalizeFilter(ciimg, 0, 255);
            filter.filter();
            
            ImageWriter writer = new TIFFWriter(ciimg, "X:/cimg");
            writer.write();
            
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }
    
    public static void test17() {
        FeatureColorConversion cc = new FeatureColorConversion();
        System.out.println(cc);   
    }
    
    public static void test18() {
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                                 "X:/medimages/nhead/seg.model");
            reader.read();
            
            Image model = reader.getImage();
            
            ColorRange cr = AnalyzerUtils.getColorRange(model);
            
            int[] count = new int[cr.getNColors()];
            Arrays.fill(count, 0);
            for (int i = 0, n = model.getNVoxels(); i < n; i++) {
                count[model.getColor(i)]++;       
            }
            
            
            ColorConversion cc = model.getColorConversion();
            int[] color = new int[3];
            for (int i = 0; i < count.length; i++) {
                cc.convert(i, color);
                System.out.println("" + i + ": " + count[i] +  "color: " + color[0] + "," + color[1] + "," + color[2]);    
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();    
        }    
    }
    
    public static void test19() {
        Timer timer = new Timer();
        Image image = new ImageData(250, 250, 250);
        
        timer.start();
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.getColor(i);
            image.setColor(i, i);    
        }
        timer.stop();
        timer.print();
            
    }
    
    public static void test20() {
        for (int i = 1; i <= 27; i++) {
            int var = (27-i)*MathUtil.sqr(i) + i*MathUtil.sqr((27-i));
            int gcd = MathUtil.gcd(var, 729);
            System.out.println("i: " + i + ", gcd: " + gcd + " , var: " + (var/gcd) + "/" + (729/gcd));
        }
    }
    
    public static void test21() {
        try {
            String modelPath = "Z:/Workspace/Projekte/Diplom/code/data/nhead/seg.ml.t1.n9.rf20";
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), modelPath);
            reader.read();
            
            Image model = reader.getImage();
            
            SecondOrder second = new SecondOrder(model);
            model = second.varianceImage();
            ImageFilter filter = new LinearNormalizeFilter(model, 0, 255);
            filter.filter();
            
            ImageWriter writer = new TIFFWriter(filter.getImage(), "c:/seg.ml.var.t1.n9.rf20");
            writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static String wordWrap(String text) {
        StringBuffer buffer = new StringBuffer(text);
        
        for (int i = 80; i < buffer.length(); i += 81) {
            buffer.insert(i, '\n');
        }
        
        return buffer.toString();
    } 
    
    
    public static void test22() {
        try {
            String path = "C:/Workspace/Projekte/Diplom/code/data/image003.tif";
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), path);
            reader.read();
            
            Image image = reader.getImage();
            
            AffineTransformation affine = AffineTransformation.getRotateInstance(new double[]{0, 0, 0.1});
            Image affineImage = affine.transform(image);
            
            int g = 10;
            GlobalInterpolator interpol = new GlobalInterpolator();
            interpol.setWeightFunction(new GlobalInterpolator.ExponentialWeightFunction(50.0));
            
            RegularDisplacementField field = FieldFactory.createRegularField(affine, image.getDimension(), g);
            field.setInterpolator(interpol);
            
                           
            showField(field, image.getDimension()); 
            
            showField((VectorField)field.createInverse(), image.getDimension());                                  
            
                                                
            Image fieldImage = field.transform(image);
            
            ImageWriter writer = new TIFFWriter(affineImage, "C:/Temp/affine.form002");
            writer.write();
            
            writer = new TIFFWriter(fieldImage, "C:/Temp/field.form002");
            writer.write();
            
            System.out.println("READY");
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    } 
    
    public static void showField(VectorField field, Dimension dim) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new VectorFieldPanel(field, dim));
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
       
        frame.setSize(dim.getSizeX(), dim.getSizeY());
        frame.show();
    }  

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test22();
    }
    
}





















