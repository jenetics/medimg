/**
 * Created on 07.10.2002 19:56:56
 *
 */
package org.wewi.medimg.util.param;

import java.util.Iterator;

import org.jdom.Element;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ParameterConverter {
    
    public ParameterConverter() {
    }
    
    public Parameter convert(Element element) {
        return null;
    }
    
    public Element convert(Parameter parameter) {
        Element element = new Element("Parameter");
        write(parameter, element);
        return element; 
    }
    
    private void write(Parameter p, Element e) {
        for (Iterator it = p.getParameterList().iterator(); it.hasNext();) {
            Parameter param = (Parameter)it.next();
            write(param, e);    
        }  
        
        e.setAttribute("name", p.getName());
        e.setAttribute("class", p.getClassName());
        e.addContent(p.getValue());
    }    

}
