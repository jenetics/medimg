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













