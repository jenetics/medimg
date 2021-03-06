/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Created on 30.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.image.ByteImageFactory;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ShortImageFactory;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Batch { 
    private String batchFile;
    private String protocolFile;
    private Document doc = null; 
    
    private String currentModelImageFileName = "";
    private String currentSourceImageFileName = "";
    private Image currentModelImage;
    private Image currentSourceImage;
    
    /**
     * Constructor for Batch.
     */
    public Batch() {
        super();
    }
    
    private void updateBatchFile() { 
        XMLOutputter out = new XMLOutputter("    ", true);
        try {
            out.output(doc, new FileOutputStream(batchFile));
        } catch (FileNotFoundException e) {
            System.err.println("Can't write back BatchFile \"" + batchFile + "\" " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Can't write back BatchFile \"" + batchFile + "\" " + e);
            System.exit(0);            
        }      
    }    
    
    public void batch(String[] args) {
        batchFile = args[0];
        protocolFile = args[1];
        
        
        
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(new File(batchFile));
        } catch (JDOMException e) {
            System.err.println("Can't create SaxBuilder: " + e);
            System.exit(0);
        } 
        
        Element batch = doc.getRootElement(); 
        List tasks = batch.getChildren("Task");
        
        //Abarbeiten der einzelnen Tasks
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            Element task = (Element)it.next();
            executeTask(task);     
        }
    }
    
    
    protected void executeTask(Element task) { 
        String id = "";
        
        int iterations = 0, iterationsDone = 0;
        try {
            id = task.getAttribute("id").getValue();
            iterations = task.getAttribute("iterations").getIntValue();
            iterationsDone  = task.getAttribute("iterations.done").getIntValue();
        } catch (DataConversionException e) {
            System.err.println("Can't convert data: " + e);
            System.exit(0);
        }
        
        if (iterations <= iterationsDone) {
            return;    
        }
        
        
        
        
        //Auswerten der Parameter
        List parameter = task.getChildren("Parameter");
        double beta = 0;
        String sourceName = null, modelName = null;
        for (Iterator it = parameter.iterator(); it.hasNext();) {
            Element param = (Element)it.next();
            String name = param.getAttribute("name").getValue();
            if ("beta".equals(name)) {
                beta = Double.parseDouble(param.getText());   
            } else if ("source_image".equals(name)) {
                sourceName = param.getText();    
            } else if ("model_image".equals(name)) {
                modelName = param.getText();
            }   
        }
        
        //Die Bilder werden nur dann geladen, wenn dies noch nicht 
        //geschehen ist.
        if (!currentSourceImageFileName.equals(sourceName)) {
            currentSourceImageFileName = sourceName;
            ImageReader reader = new TIFFReader(ShortImageFactory.getInstance(), 
                                                   new File(currentSourceImageFileName)); 
            try {
                reader.read();
            } catch (ImageIOException e) {
                System.err.println("Kann Bild \"" + currentSourceImage + "\" nicht laden.\n" + e);
                System.err.println("Fahre mit dem nächsten Task fort.");
                return;
            }
            currentSourceImage = reader.getImage();  
        } 
        if (!currentModelImageFileName.equals(modelName)) {
            currentModelImageFileName = modelName;
            ImageReader reader = new TIFFReader(ByteImageFactory.getInstance(), 
                                                   new File(currentModelImageFileName)); 
            try {
                reader.read();
            } catch (ImageIOException e) {
                System.err.println("Kann Bild \"" + currentSourceImage + "\" nicht laden.\n" + e);
                System.err.println("Fahre mit dem nächsten Task fort.");
                return;
            }
            currentModelImage = reader.getImage();  
        }
        ////////////////////////////////////////////////////////////////////////
        

        for (int i = iterationsDone; i < iterations; i++) {  
            System.out.println("Task ID: " + id + " Iteration: " + i);            
                     
            //Auswerten des Segmentierten Bildes
            MAPValidator validator = new MAPValidator();
            validator.setProtocolFile(protocolFile +
                                       "/protocol." + System.currentTimeMillis() + "." +
                                       id + "." + i  + ".xml");                                                            
            validator.setK(6);
            validator.setBeta(beta);
            validator.setModelImage(currentModelImage);
            validator.setSourceImage(currentSourceImage);
            validator.validate(); 
            
            task.setAttribute("iterations.done", Integer.toString(i+1));
            updateBatchFile();
        }                    
    } 
    
    
    
    public static void main(String[] args) {
        Batch batch = new Batch();
        batch.batch(args);     
    }   

}

























