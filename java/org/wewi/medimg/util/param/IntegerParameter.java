/**
 * Created on 08.10.2002 10:25:08
 *
 */
package org.wewi.medimg.util.param;

import org.jdom.Element;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IntegerParameter extends Parameter {
    private int value;


    public IntegerParameter() {
        this("", 0); 
    }
    
    public IntegerParameter(int value) {
        this("", value);    
    }
    
    public IntegerParameter(String name, int value) {
        super(name);
        this.value = value;    
    }

    /**
     * @see org.wewi.medimg.util.param.Parameter#createParameter(Element)
     */
    public Parameter initParameter(Element xml) {
        name = xml.getAttribute("name").getValue();
        value = Integer.parseInt(xml.getText());
        
        return this;
    }

    /**
     * @see org.wewi.medimg.util.param.Parameter#createParameterElement()
     */
    public Element createParameterElement() {
        Element e = new Element("Parameter");
        e.setAttribute("name", name);
        e.setAttribute("class", clazz);
        e.setText(Integer.toString(value));
        
        return e;
    }

    /**
     * @see org.wewi.medimg.util.param.Parameter#getParameterObject()
     */
    public Object getParameterObject() {
        return new Integer(value);
    }
    

}
