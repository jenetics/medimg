/**
 * Created on 07.10.2002 18:31:34
 *
 */
package org.wewi.medimg.util.param;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jdom.Element;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.io.BMPReader;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.JPEGReader;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.TIFFReader;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageParameter extends Parameter {
    private String file;
    private ImageFactory factory;
    private Class readerClass;
    
    private Image image = null;
    
    public ImageParameter() {
        super();    
    }
    
    public ImageParameter(String name) {
        super(name);    
    }
    
    public ImageParameter(String name, ImageFactory factory, Class reader, String file) {
        this(name);
        this.factory = factory;
        this.readerClass = reader;
        this.file = file;    
    }



	public Element createParameterElement() {
        Element e = new Element("Parameter");
        e.setAttribute("name", name);
        e.setAttribute("class", clazz);
        e.setAttribute("image.factory", factory.getClass().getName());
        e.setAttribute("image.reader", readerClass.getName());
        e.setText(name);
        
		return e;
	}


	public Object getParameterObject() {
        if (image != null) {
            return image;            
        }
        
        ImageReader reader = null;
        
        try {
			Constructor c = readerClass.getConstructor(new Class[]{ImageFactory.class, String.class});
		    reader = (ImageReader)c.newInstance(new Object[]{factory, file});
		} catch (NoSuchMethodException e) {
            System.err.println("ImageParameter.getParameterObject:" + e);
            return null;
		} catch (InstantiationException e) {
            System.err.println("ImageParameter.getParameterObject:" + e);
            return null;            
        } catch (IllegalAccessException e) {
            System.err.println("ImageParameter.getParameterObject:" + e);
            return null;            
        } catch (InvocationTargetException e) {
            System.err.println("ImageParameter.getParameterObject:" + e);
            return null;            
        }
        
        
        
        try {
			reader.read();
		} catch (ImageIOException e) {
            System.err.println("ImageParameter.getParameterObject(): " + e);
		}
        image = reader.getImage();
        
		return image;
	}


	public Parameter initParameter(Element xml) {
        name = xml.getAttribute("name").getValue();
        String factoryName = xml.getAttribute("image.factory").getValue();
        String readerName = xml.getAttribute("image.reader").getValue();
        file = xml.getText();
        
        if (factoryName.equals(ImageDataFactory.class.getName())) {
            factory = ImageDataFactory.getInstance();    
        } else {
            factory = ImageDataFactory.getInstance();    
        }
        
        ImageReader reader = null;
        
        if (readerName.equals(TIFFReader.class.getName())) {
            reader = new TIFFReader(factory, file);        
        } else if (readerName.equals(BMPReader.class.getName())) {
            reader = new BMPReader(factory, file);        
        } else if (readerName.equals(BMPReader.class.getName())) {
            reader = new JPEGReader(factory, file);        
        } else if (readerName.equals(BMPReader.class.getName())) {
            reader = new JPEGReader(factory, file);        
        } else if (readerName.equals(RawImageReader.class.getName())) {
            reader = new RawImageReader(factory, file);        
        } else {
            reader = new TIFFReader(factory, file);
        }
        
		return this;
	}
    
    
    public static void main(String[] args) {
        Parameter p = new ImageParameter("", ImageDataFactory.getInstance(), null, "");    
    }

}















