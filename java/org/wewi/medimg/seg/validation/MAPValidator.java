/**
 * Created on 08.10.2002 10:51:30
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.MinMaxOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;
import org.wewi.medimg.seg.stat.MAPKMeansClusterer;
import org.wewi.medimg.util.*;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPValidator {
    private MAPKMeansClusterer clusterer;
    private Image source;
    private Image target;
    private Image anatomicalModel;
    
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
        this.source = source;    
    }
    
    public void setAnatomicalModel(Image am) {
        anatomicalModel = am;
    }
    
    public void setK(int k) {
        this.k = k;    
    }
    
    public void setBeta(double b) {
        this.beta = b;    
    }
    
    public void validate() {
        clusterer = new MAPKMeansClusterer(k ,beta); 
        
        startTime = System.currentTimeMillis();
        target = clusterer.segment(source);
        stopTime = System.currentTimeMillis();
        
        MinMaxOperator op = new MinMaxOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(anatomicalModel, op);
        analyzer.analyze(); 
        ColorRange cr = new ColorRange(op.getMinimum(), op.getMaximum());       
        
        AccumulatorArray accu = new AccumulatorArray(cr.getNColors(), k);
        for (int i = 0, n = source.getNVoxels(); i < n; i++) {
            accu.inc(anatomicalModel.getColor(i), target.getColor(i));   
        }
        

        T3 t3 = new T3(accu);
        ErrorMeasure error = new ErrorMeasure(anatomicalModel,target, t3);
        error.measure();
          
          
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
        param.addContent(Util.transform(source));
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
        result.addContent(Util.transform(accu));
        result.addContent(Util.transform(t3));
        result.addContent(Util.transform(error));
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
   
}
