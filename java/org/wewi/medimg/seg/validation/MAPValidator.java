/**
 * Created on 08.10.2002 10:51:30
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImageHeader;
import org.wewi.medimg.image.ImageProperties;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.statistic.SecondOrder;
import org.wewi.medimg.seg.stat.MAPKMeansClusterer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPValidator {
    private MAPKMeansClusterer clusterer;
    private Image sourceImage;
    private Image targetImage;
    private Image modelImage;
    
    //Parameters
    private int k;
    private double beta;
    
    private long startTime;
    private long stopTime;
    
    private String protocolFile;
    
    public MAPValidator() {
    } 
    
    public void setProtocolFile(String fileName) {
        protocolFile = fileName;
    }     
    
    public void setSourceImage(Image source) {
        this.sourceImage = source;    
    }
    
    public void setTargetImage(Image target) {
        this.targetImage = target;
    }
    
    public void setModelImage(Image am) {
        modelImage = am;
    }
    
    public void setK(int k) {
        this.k = k;    
    }
    
    public void setBeta(double b) {
        this.beta = b;    
    }
    
    public void validate() {
        clusterer = new MAPKMeansClusterer(k ,beta); 
        
        //Segmentationprocedure   
        startTime = System.currentTimeMillis();
        //targetImage = clusterer.segment(sourceImage);
        stopTime = System.currentTimeMillis();
        
        Properties imgProp = new Properties();
        imgProp.setProperty("k", Integer.toString(k));
        imgProp.setProperty("beta", Double.toString(beta));
        //targetImage.getHeader().addImageProperties(imgProp);
        
        //ValidationMeasure
        ValidationMeasure measure = new VarianceImageMeasure();//new SaveImageMeasure();//new ReverseMLMeasure();//new MIMeasure();
        double err = measure.measure(modelImage, targetImage);
        
        /*
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(modelImage, op);
        analyzer.analyze(); 
        ColorRange cr = new ColorRange(op.getMinimum(), op.getMaximum());       
        
        AccumulatorArray accu = new AccumulatorArray(cr.getNColors(), k);
        for (int i = 0, n = sourceImage.getNVoxels(); i < n; i++) {
            accu.inc(modelImage.getColor(i), targetImage.getColor(i));   
        }
        

        T3 t3 = new T3(accu);
        ErrorMeasure error = new ErrorMeasure(modelImage,targetImage, t3);
        error.measure();
        */  
          
        Element protocol = new Element("Protocol");
        
        //Daten zum Algorithmus
        Element alg = new Element("Algorithm");
        alg.setAttribute(new Attribute("class", clusterer.getClass().getName()));
        Element param = new Element("Parameter");
        param.setAttribute("name", "k");
        param.setAttribute("type", Integer.class.getName());
        param.addContent(Integer.toString(k));
        alg.addContent(param);
        param= new Element("Parameter");
        param.setAttribute("name", "b");
        param.setAttribute("type", Double.class.getName());
        param.addContent(Double.toString(beta));
        alg.addContent(param);
        param = new Element("Parameter");
        param.setAttribute("name", "image");
        param.addContent(XMLUtil.transform(targetImage));
        alg.addContent(param);
        //Ergebnisse des Algorithmus
        Element algResult = new Element("Result");
        Element exeTime = new Element("ExecutionTime");
        exeTime.setAttribute(new Attribute("start", Long.toString(startTime)));
        exeTime.setAttribute(new Attribute("stop", Long.toString(stopTime)));
        algResult.addContent(exeTime);
        Element itCount = new Element("Iterations");
        itCount.addContent(Integer.toString(clusterer.getIterations()));
        algResult.addContent(itCount);
        
        Element mean = new Element("MeanValues");
        Element value;
        double[] mv = clusterer.getMeanValues();
        mean.setAttribute("means", Integer.toString(mv.length));
        for (int i = 0; i < mv.length; i++) {
            value = new Element("Value");
            value.addContent(Double.toString(mv[i]));   
            mean.addContent(value); 
        }
        algResult.addContent(mean);
        alg.addContent(algResult);
        protocol.addContent(alg); 
        
        //Ergebnis
        Element result = new Element("Result");
        //result.addContent(XMLUtil.transform(accu));
        //result.addContent(XMLUtil.transform(t3));
        //result.addContent(XMLUtil.transform(error));
        result.addContent((new Element("MutualInformation")).addContent(Double.toString(err)));
        protocol.addContent(result);
        
        Document doc = new Document(protocol);
        
        XMLOutputter out = new XMLOutputter("    ", true);
        
        
        try {
            out.output(doc, new FileOutputStream(protocolFile));
        } catch (FileNotFoundException e) {
            System.err.println("MAPValidator: " + e);
        } catch (IOException e) {
            System.err.println("MAPValidator: " + e);
        }
        
        
    }
    
    
    
    
    public static void main(String[] args) {   
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMaximumFractionDigits(3);
        format.setMinimumFractionDigits(3);
        format.setMinimumIntegerDigits(1);
        
        try {
        
            String imagePath = "Z:/Workspace/Projekte/Diplom/code/data/segimg/t1.n9.rf20";
            File[] files = (new File(imagePath)).listFiles();
            File modelFile = new File("Z:/Workspace/Projekte/Diplom/code/data/nhead/seg.model");
        
            ImageReader tiffReader = new TIFFReader(ImageDataFactory.getInstance(), modelFile);
            tiffReader.read();
            Image modelImage = tiffReader.getImage();
            SecondOrder so = new SecondOrder(modelImage); 
            modelImage = so.varianceImage();
            
            for (int i = 0; i < files.length; i++) {
                System.out.println("" + (i+1) + "-" + files.length + ": " + files[i].toString());
                ImageReader reader = new RawImageReader(ImageDataFactory.getInstance(), files[i]);
                reader.read();
                
                Image image = reader.getImage();
                //System.out.println(image);
                ImageHeader header = image.getHeader(); 
                
                ImageProperties prop = header.getImageProperties();
                int k = Integer.parseInt(prop.getProperty("k"));
                double beta = Double.parseDouble(prop.getProperty("BEAT"));
                
                so = new SecondOrder(image);
                image = so.varianceImage();
                
                MAPValidator validator = new MAPValidator();
                validator.setK(k);
                validator.setBeta(beta);
                validator.setTargetImage(image);
                validator.setModelImage(modelImage);
                validator.setProtocolFile(
                "C:/Workspace/Projekte/Diplom/code/data/validation/map/protocols/" +
                format.format(k) + "-" + format.format(beta) + "-" + System.currentTimeMillis() + ".xml");
                
                validator.validate();
                
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
        }
        
        
    }
   
}
