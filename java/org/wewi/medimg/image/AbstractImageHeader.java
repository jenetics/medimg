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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.util.StringInputStream;
import org.wewi.medimg.util.StringOutputStream;



/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.2
 */
class AbstractImageHeader implements ImageHeader {
    private AbstractImage image;
    
    private Dimension dim;
    private Properties properties;
    
    private Logger logger;

    public AbstractImageHeader(AbstractImage image) {
        this.image = image;
        properties = new Properties();
        
        logger = Logger.getLogger(getClass().getName());
    }
    
    /**
     * Schreiben des Headers als XML
     */
    private void writeXML(OutputStream out) throws IOException {
        Element root = new Element("ImageHeader");
        root.setAttribute("class", getClass().getName());
        Document doc = new Document(root);
        
        //Schreiben der Abmessungen
        Dimension dim = image.getDimension();
        Element dimension = new Element("Dimension");
        dimension.addContent((new Element("MinX")).addContent(Integer.toString(dim.getMinX())));
        dimension.addContent((new Element("MaxX")).addContent(Integer.toString(dim.getMaxX())));
        dimension.addContent((new Element("MinY")).addContent(Integer.toString(dim.getMinY())));
        dimension.addContent((new Element("MaxY")).addContent(Integer.toString(dim.getMaxY())));
        dimension.addContent((new Element("MinZ")).addContent(Integer.toString(dim.getMinZ())));
        dimension.addContent((new Element("MaxZ")).addContent(Integer.toString(dim.getMaxZ())));
        root.addContent(dimension);

                
        //Schreiben der Art der ColorConversion
        Element cc = new Element("ColorConversion");
        cc.setAttribute("class", image.getColorConversion().getClass().getName());
        StringOutputStream sout = new StringOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(sout);
        oout.writeObject(image.getColorConversion());
        oout.close();
        cc.addContent(sout.getOutputString());
        root.addContent(cc);
        
        //Schreiben des Farbbereichs (ColorRange)
        ColorRange colorRange = image.getColorRange();
        Element cr = new Element("ColorRange");
        cr.addContent((new Element("MinColor")).addContent(Integer.toString(colorRange.getMinColor())));
        cr.addContent((new Element("MaxColor")).addContent(Integer.toString(colorRange.getMaxColor()))); 
        root.addContent(cr);  
        
        //Schreiben der ImageProperties
        root.addContent(new Comment("Die ImageProperties können beliebig verwendet werden"));
        Element prop = new Element("ImageProperties");
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
            String name = (String)e.nextElement();
            prop.addContent((new Element(name)).addContent(properties.getProperty(name)));
        } 
        root.addContent(prop);            
        
        
        XMLOutputter outputter = new XMLOutputter("    ", true);
        outputter.output(doc, out);
    }
    
    /**
     * Lesen des Headers aus dem InputStream
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
        
        //Auslesen der Dimension
        Element dimension = root.getChild("Dimension");
        Dimension dim = new Dimension(Integer.parseInt(dimension.getChildText("MinX")),
                                      Integer.parseInt(dimension.getChildText("MaxX")),
                                      Integer.parseInt(dimension.getChildText("MinY")),
                                      Integer.parseInt(dimension.getChildText("MaxY")),
                                      Integer.parseInt(dimension.getChildText("MinZ")),
                                      Integer.parseInt(dimension.getChildText("MaxZ")));
        
                                      
                                      
        //Einlesen und Erzeugen der ColorConverion
        ColorConversion cc = new GreyColorConversion();
        Element ccElement = root.getChild("ColorConversion");
        String ccString = root.getChildText("ColorConversion");
        StringInputStream sin = new StringInputStream(ccString);
        ObjectInputStream oin = new ObjectInputStream(sin);
        try {
			cc = (ColorConversion)oin.readObject();
		} catch (IOException e) {
            logger.log(Level.WARNING, "Can't read ColorConversion");
            throw new IOException("" + e);
		} catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Can't read ColorConversion");
            throw new IOException("" + e);
		}
 
        
        //Einlesen der ImageProperties
        Element prop = root.getChild("ImageProperties");
        if (prop == null) {
            return;    
        }
        List list = prop.getChildren();
        Element element;
        for (Iterator it = list.iterator(); it.hasNext();) {
            element = (Element)it.next();
            properties.setProperty(element.getName(), element.getText());
        }
        
        
        //"Aufblasen" des Image auf die richtige Größe.
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
	public Properties getImageProperties() {
		return properties;
	}

	/**
	 * @see org.wewi.medimg.image.ImageHeader#setImageProperties(Properties)
	 */
	public void setImageProperties(Properties prop) {
        for (Enumeration enum = prop.keys(); enum.hasMoreElements();) {
            String key = (String)enum.nextElement();
            properties.setProperty(key, prop.getProperty(key)); 
        }
	}
    
    public void addImageProperties(Properties prop) {
        for (Enumeration enum = prop.keys(); enum.hasMoreElements();) {
            String key = (String)enum.nextElement();
            properties.setProperty(key, prop.getProperty(key));    
        }    
    }
    
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
            String name = (String)e.nextElement();
            buffer.append(name).append(":").append(properties.getProperty(name)).append("\n");
        }            
        return buffer.toString();
    }

}
