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
 * AbstractImageHeader.java
 *
 * Created on 22. Februar 2002, 10:42
 */

package org.wewi.medimg.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.util.StringInputStream;
import org.wewi.medimg.util.StringOutputStream;



/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
class AbstractImageHeader implements ImageHeader {
    private static Logger logger = Logger.getLogger("org.wewi.medimg.image");
    
    private AbstractImage image;   
    //private Dimension dim;    
    private ImageProperties properties;

    public AbstractImageHeader(AbstractImage image) {
        this.image = image;
        properties = new ImageProperties();
    }
    
    private String wordWrap(String text) {
        StringBuffer buffer = new StringBuffer(text);
        
        for (int i = 80; i < buffer.length(); i += 81) {
            buffer.insert(i, '\n');
        }
        
        return buffer.toString();
    }
    
    private String removeNewLine(String text) {
        return text.replaceAll("\n", "");
    }
    
    /**
     * Method for converting a Dimension to a XML-Element (jdom)
     * 
     * @param dim Dimension to convert
     * @return Element converted Dimension
     */
    private Element toXML(Dimension dim) {
        Element element = new Element("Dimension");
        
        element.addContent((new Element("MinX")).addContent(Integer.toString(dim.getMinX())));
        element.addContent((new Element("MaxX")).addContent(Integer.toString(dim.getMaxX())));
        element.addContent((new Element("MinY")).addContent(Integer.toString(dim.getMinY())));
        element.addContent((new Element("MaxY")).addContent(Integer.toString(dim.getMaxY())));
        element.addContent((new Element("MinZ")).addContent(Integer.toString(dim.getMinZ())));
        element.addContent((new Element("MaxZ")).addContent(Integer.toString(dim.getMaxZ())));
        element.addContent((new Element("Step")).addContent(Integer.toString(dim.getStep())));
        
        return element;
    }
    
    /**
     * Method for converting a XML-Element to a Dimension-Object
     * 
     * @param element
     * @return Dimension
     */
    private Dimension toDimension(Element element) {
        if (element == null) {
            return new Dimension(0, 0, 0);
        }
        
        Dimension dim = null;
        try {
            dim = new Dimension(Integer.parseInt(element.getChildText("MinX")),
                              Integer.parseInt(element.getChildText("MaxX")),
                              Integer.parseInt(element.getChildText("MinY")),
                              Integer.parseInt(element.getChildText("MaxY")),
                              Integer.parseInt(element.getChildText("MinZ")),
                              Integer.parseInt(element.getChildText("MaxZ")));             
        } catch (Exception e) {
            dim = new Dimension(0, 0, 0);
        }
       
        return dim;
    }
    
    /**
     * Method for converting a ColorConversion to a XML-Element
     * 
     * @param cc
     * @return Element
     */
    private Element toXML(ColorConversion cc) {
        Element element = new Element("ColorConversion");
        element.addContent(new Comment(cc.toString()));
        element.setAttribute("class", image.getColorConversion().getClass().getName());
        
        StringOutputStream sout = new StringOutputStream();
        ObjectOutputStream oout;
        try {
            oout = new ObjectOutputStream(sout);
            oout.writeObject(cc);
            oout.close(); 
            element.addContent(wordWrap(sout.getOutputString()));
        } catch (IOException e) {
            //do nothing here
        }      
        
        return element;
    }
    
    /**
     * Method for convering a XML-Element to a ColorConversion-Object
     * 
     * @param element
     * @return ColorConversion
     */
    private ColorConversion toColorConversion(Element element) {
        if (element == null) {
            return new GreyColorConversion();
        }
        
        ColorConversion cc = new GreyColorConversion();
        try {
            StringInputStream sin = new StringInputStream(removeNewLine(element.getTextTrim()));          
            ObjectInputStream oin = new ObjectInputStream(sin);
            cc = (ColorConversion)oin.readObject();
        } catch (Exception e) {
            //Plan B
            logger.info("Plan B (ColorConversion): " + e);
            System.out.println(e);
            String clazz = element.getAttributeValue("class");
            try {
                cc = (ColorConversion)Class.forName(clazz).newInstance();
            } catch (Exception ce){
            }
        }
        
        
        return cc;
    }
    
    /**
     * Method for converting a ColorRange to a XML-Element
     * 
     * @param cr
     * @return Element
     */
    private Element toXML(ColorRange cr) {
        Element element = new Element("ColorRange");
        
        element.addContent((new Element("MinColor")).addContent(Integer.toString(cr.getMinColor())));
        element.addContent((new Element("MaxColor")).addContent(Integer.toString(cr.getMaxColor())));   
              
        return element;
    }
    
    /**
     * Method for converting a XML-Element to a ColorRange-Object
     * 
     * @param element
     * @return ColorRange
     */
    private ColorRange toColorRange(Element element) {
        if (element == null) {
            return new ColorRange(0,0);
        }
        
        int min, max;
        try {
            min = Integer.parseInt(element.getChildText("MinColor"));
            max = Integer.parseInt(element.getChildText("MaxColor"));
        } catch (NumberFormatException e) {
            min = 0; max = 0;
        }
         
        return new ColorRange(min, max);
    }
    
    
    private Element toXML(ImageProperties properties) {
        Element element = new Element("ImageProperties");
        
        Map.Entry entry;
        String key, value;
        Element e;
        for (Iterator it = properties.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            
            e = new Element(key);
            e.addContent(value);
            element.addContent(e);
        }
        
        return element;
    }
    
    private ImageProperties toImageProperties(Element element) {
        ImageProperties prop = new ImageProperties();
        if (element == null) {
            return prop;
        }
        
        List children = element.getChildren();
        Element child;
        for (Iterator it = children.iterator(); it.hasNext();) {
            child = (Element)it.next();
            prop.setProperty(child.getName(), child.getText());
        }
        
        return prop;
    }
    
    /**
     * Writing the Header in XML format.    
     */
    private void writeXML(OutputStream out) throws IOException {
        Element root = new Element("ImageHeader");
        root.setAttribute("class", getClass().getName());
        
        //Writing the dimension of the image
        root.addContent(toXML(image.getDimension()));
          
        //Writing the ColorConversion
        root.addContent(toXML(image.getColorConversion()));
        
        //Writing the ColorRange
        root.addContent(toXML(AnalyzerUtils.getColorRange(image)));  
        
        //Writing the ImageProperties
        root.addContent(toXML(properties));            
        
        
        //Writing the header to the OutputStream
        XMLOutputter outputter = new XMLOutputter("    ", true);
        outputter.output(new Document(root), out);
    }
    
    /**
     * Reading the ImageHeader from an InputStream
     */
    private void readXML(InputStream in) throws IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(in);
        } catch (JDOMException e) {
            throw new IOException("AbstractImageHeader: " + e.toString()); 
        }
        
        Element root = doc.getRootElement();
        
        //Reading the Dimension
        Dimension dim = toDimension(root.getChild("Dimension"));
                                                                      
        //Reading the ColorConversion
        ColorConversion cc = toColorConversion(root.getChild("ColorConversion"));
        
        //Einlesen der ImageProperties
        properties = toImageProperties(root.getChild("ImageProperties"));
        
        //Inflate the image to the right size.
        image.init(dim, this); 
        image.setColorConversion(cc);
    }
    
    
    /**
     * @see org.wewi.medimg.image.ImageHeader#read(InputStream)
     */
    public void read(InputStream in) throws IOException {
        readXML(in);
    }

    /**
     * @see org.wewi.medimg.image.ImageHeader#write(OutputStream)
     */
    public void write(OutputStream out) throws IOException {
        writeXML(out);
    }

    /**
     * @see org.wewi.medimg.util.Nullable#isNull()
     */
    public boolean isNull() {
        return false;
    }


    /**
     * @see org.wewi.medimg.image.ImageHeader#getImageProperties()
     */
    public ImageProperties getImageProperties() {
        return properties;
    }
    
    public void setImageProperties(ImageProperties properties){
        this.properties = properties;
    }


    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        Map.Entry entry;
        String key, value;
        for (Iterator it = properties.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            
            buffer.append(key).append(":").append(value).append("\n");
        }
                  
        return buffer.toString();
    }

}
