/*
 * ImageDataHeader.java
 *
 * Created on 22. Februar 2002, 10:42
 */

package org.wewi.medimg.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
class ImageDataHeader implements ImageHeader {
    private ImageData image;
    private Dimension dim;
    
    private Properties properties;

    public ImageDataHeader(ImageData image) {
        this.image = image;
        properties = new Properties();
    }
    
    
    private void writeXML(OutputStream out) throws IOException {
        Element root = new Element("ImageHeader");
        root.setAttribute("class", this.getClass().getName());
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
        cc.addContent(image.getColorConversion().getClass().getName());
        root.addContent(cc);
        
        //Schreiben des Farbbereichs (ColorRange)
        ColorRange colorRange = image.getColorRange();
        Element cr = new Element("ColorRange");
        cr.addContent((new Element("MinColor")).addContent(Integer.toString(colorRange.getMinColor())));
        cr.addContent((new Element("MaxColor")).addContent(Integer.toString(colorRange.getMaxColor()))); 
        root.addContent(cr);  
        
        //Schreiben der Properties
        root.addContent(new Comment("Diese ImageProperties können beliebig verwendet werden"));
        Element prop = new Element("ImageProperties");
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
            String name = (String)e.nextElement();
            prop.addContent((new Element(name)).addContent(properties.getProperty(name)));
        } 
        root.addContent(prop);            
        
        
        XMLOutputter outputter = new XMLOutputter("    ", true);
        outputter.output(doc, out);
    }
    
    private void readXML(InputStream in) throws IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
			doc = builder.build(in);
		} catch (JDOMException e) {
            throw new IOException("ImageDataHeader: " + e.toString()); 
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
        image.init(dim);
        
                                      
                                      
        //Erzeugen der ColorConverion
        ColorConversion cc = new GreyColorConversion();
        try {
			cc = (ColorConversion)Class.forName(root.getChildText("ColorConversion")).newInstance();
		} catch (InstantiationException e) {
            throw new IOException("" + e);
		} catch (IllegalAccessException e) {
            throw new IOException("" + e);
		} catch (ClassNotFoundException e) {
            throw new IOException("" + e);
		}
        image.setColorConversion(cc); 
        
        //Einlesen der Properties
        Element prop = root.getChild("ImageProperties");
        if (prop == null) {
            return;    
        }
        List list = prop.getChildren();
        Element element;
        for (Iterator it = list.iterator(); it.hasNext();) {
            element = (Element)it.next();
            properties.put(element.getName(), element.getContent());
        }
         
    }
    
    
	/**
	 * @see org.wewi.medimg.image.ImageHeader#read(InputStream)
	 */
	public void read(InputStream in) throws IOException {
        readXML(in);
	}

	/**
	 * @see org.wewi.medimg.image.ImageHeader#readDimension(InputStream)
	 */
	public Dimension readDimension(InputStream in) throws IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(in);
        } catch (JDOMException e) {
            throw new IOException("ImageDataHeader: " + e.toString()); 
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
                                      
        return dim;
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
        properties = prop;
	}

}








