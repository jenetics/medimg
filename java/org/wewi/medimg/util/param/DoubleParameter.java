/**
 * Created on 07.10.2002 19:09:51
 *
 */
package org.wewi.medimg.util.param;

import org.jdom.Element;
import org.jdom.output.XMLOutputter;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleParameter extends Parameter {
    private double value;


    public DoubleParameter() {
        this("", 0); 
    }
    
    public DoubleParameter(double value) {
        this("", value);    
    }
    
    public DoubleParameter(String name, double value) {
        super(name);
        this.value = value;    
    }

	public Parameter initParameter(Element xml) {
        name = xml.getAttribute("name").getValue();
        value = Double.parseDouble(xml.getText());
        
		return this;
	}

	public Element createParameterElement() {
        Element e = new Element("Parameter");
        e.setAttribute("name", name);
        e.setAttribute("class", clazz);
        e.setText(Double.toString(value));
        
		return e;
	}

	public Object getParameterObject() {
		return new Double(value);
	}
    
    
    public static void main(String[] args) {
        XMLOutputter out = new XMLOutputter();   
        DoubleParameter p = new DoubleParameter("BETA", 0.323);
        System.out.println(out.outputString(p.createParameterElement())); 
    }

}













