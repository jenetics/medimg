/**
 * Created on 07.10.2002 18:28:06
 *
 */
package org.wewi.medimg.util.param;

import org.jdom.Element;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Parameter {
    protected String name = "";
    protected String clazz;
    
    protected Parameter() {
        this("empty");
    }
    
    protected Parameter(String name) {
        clazz = getClass().getName();
        this.name = name;    
    }
    
    public abstract Parameter initParameter(Element xml);
    
    public abstract Element createParameterElement();
    
    public String getParameterName() {
        return name;    
    }
    
    public abstract Object getParameterObject();    
}
