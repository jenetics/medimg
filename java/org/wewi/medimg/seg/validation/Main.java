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
import org.wewi.medimg.image.IntImageFactory;
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
            ImageReader reader = new TIFFReader(IntImageFactory.getInstance(),
                                            new File("X:/medimages/nhead/seg.model"));
            reader.read();
            mimg = reader.getImage();
            System.out.println("Lesen des Bildes");
            reader = new TIFFReader(IntImageFactory.getInstance(),
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





