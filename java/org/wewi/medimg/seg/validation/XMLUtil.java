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
 * Created on 27.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.Iterator;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Element;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageProperties;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class XMLUtil {

    private XMLUtil() {
    }
    
    static Element transform(AccumulatorArray accu) {
        Element table = new Element("FrequencyMatrix");
        table.setAttribute("rows", Integer.toString(accu.getRows()));
        table.setAttribute("cols", Integer.toString(accu.getCols()));
        
        Element td, tr;
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            tr = new Element("Row");
            tr.setAttribute("no", Integer.toString(i));
            for (int j = 0, m = accu.getCols(); j < m; j++) {
                td = new Element("ColData");
                td.setAttribute("no", Integer.toString(j));
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
        Element imageMetaData = new Element("ImageHeader"); 
        
        ImageProperties prop = null;
        if (image == null) {
            prop = new ImageProperties();
        } else {
            prop = image.getHeader().getImageProperties();
        }
        
        Map.Entry entry;
        String key, value;
        for (Iterator it = prop.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            imageMetaData.addContent((new Element(key)).addContent(value));
        }        
        
        return imageMetaData;   
    }
    
    static Element transform(Object o) {
        return null;    
    }

}



























