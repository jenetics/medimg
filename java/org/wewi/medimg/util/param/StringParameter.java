/**
 * Created on 07.10.2002 19:19:40
 *
 */
package org.wewi.medimg.util.param;

import org.jdom.Element;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class StringParameter extends Parameter {
    private String value;

    public StringParameter() {
        super();
    }
    
    public StringParameter(String value) {
        super(); 
        this.value = value;   
    }
    
    public StringParameter(String name, String value) {
        super(name);
        this.value = value;        
    }

	public Element createParameterElement() {
        Element e = new Element("Parameter");
        e.setAttribute("name", name);
        e.setAttribute("class", clazz);
        e.setText(value);
        
        return e;
	}


	public Object getParameterObject() {
		return new String(value);
	}

	public Parameter initParameter(Element xml) {
        name = xml.getAttribute("name").getValue();
        value = xml.getText();
        
        return this;
	}

}
