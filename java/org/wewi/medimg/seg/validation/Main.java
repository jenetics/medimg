/**
 * Created on 19.08.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.stat.MLKMeansClusterer;

/**
 * @author Franz Wilhelmstötter
 *
 */
public final class Main {
    
    public void batch(String[] args) {
        String todo = args[0];
        String done = args[1];
        
        
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(new File(todo));
        } catch (JDOMException e) {
            System.err.println("Can't create SaxBuilder: " + e);
            return;
        } 
        
        Element batch = doc.getRootElement(); 
        List tasks = batch.getChildren("Task");
        
        //Abarbeiten der einzelnen Tasks
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            Element task = (Element)it.next();
            task(task);     
        }
    }
    
    
    private void task(Element task) {
        
    }
    
    
    
    
    
    
    public static void test() {
        Image oimg = new NullImage();
        Image mimg = new NullImage();
        Image simg = new NullImage();
        
        ObservableSegmenter seg = null;
        seg = new MLKMeansClusterer(5);
        //seg.addLoggerHandler(new ConsoleHandler());
        
        //Einlesen der Bilder
        try {
            System.out.println("Lesen des Modells");
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                            new File("X:/medimages/nhead/seg.model"));
            reader.read();
            mimg = reader.getImage();
            System.out.println("Lesen des Bildes");
            reader = new TIFFReader(ImageDataFactory.getInstance(),
                                            new File("X:/medimages/nhead/t1.n3.rf20"));
            reader.read();
            oimg = reader.getImage();
            
            System.out.println(oimg.getHeader().toString());
            
        } catch (Exception e) {
            e.printStackTrace(); 
            return;   
        }
        
        MLValidator validator = new MLValidator();
        validator.setProtocolFile("C:/Workspace/fwilhelm/Projekte/Diplom/validation/" +
                                   "protocol." + 
                                   Long.toString(System.currentTimeMillis()) + ".xml");                                                            
        validator.setK(4);
        validator.setAnatomicalModel(mimg);
        validator.setSourceImage(oimg);
        
        validator.validate();        
    }

    public static void main(String[] args) {

        
    }
}





