/**
 * Created on 27.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.Enumeration;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Element;
import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class Util {

    private Util() {
    }
    
    static Element transform(AccumulatorArray accu) {
        Element table = new Element("FrequencyMatrix");
        
        Element td, tr;
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            tr = new Element("Row");
            for (int j = 0, m = accu.getCols(); j < m; j++) {
                td = new Element("ColData");
                td.addContent(Integer.toString(accu.getValue(i, j)));
                tr.addContent(td);   
            }
            table.addContent(tr);       
        }
        
        
        return table;    
    }
    
    static Element transform(T3 t3) {
        Element transform = new Element("T3");
        Element map;
        for (int i = 0, n = t3.getAbstractFeatures(); i < n; i++) {
            map = new Element("Map");
            map.setAttribute(new Attribute("source", Integer.toString(i))); 
            map.setAttribute(new Attribute("target", Integer.toString(t3.transform(i)))); 
            transform.addContent(map);      
        } 
        
        return transform;   
    }
    
    static Element transform(ErrorMeasure em) {
        Element error = new Element("Error");
        double[] featureError = em.getFeatureError();
        double overallError = em.getOverallError();
        
        Element oe = new Element("OverallError");
        oe.addContent(Double.toString(overallError));
        error.addContent(oe);
        
        Element fe;
        for (int i = 0; i < featureError.length; i++) {
            fe = new Element("FeatureError");
            fe.setAttribute(new Attribute("feature", Integer.toString(i)));
            fe.addContent(Double.toString(featureError[i])); 
            error.addContent(fe);   
        }
        
        return error;    
    }
    
    static Element transform(Image image) {
        Element imageMetaData = new Element("ImageMetaData"); 
        Properties prop = image.getHeader().getImageProperties();
        
        for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
            String name = (String)e.nextElement();
            imageMetaData.addContent((new Element(name)).addContent(prop.getProperty(name)));
        }
        
        return imageMetaData;   
    }
    
    static Element transform(Object o) {
        return null;    
    }

}



























