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

    public Parameter initParameter(Element xml) {
        name = xml.getAttribute("name").getValue();
        value = Integer.parseInt(xml.getText());
        
        return this;
    }

    public Element createParameterElement() {
        Element e = new Element("Parameter");
        e.setAttribute("name", name);
        e.setAttribute("class", clazz);
        e.setText(Integer.toString(value));
        
        return e;
    }

    public Object getParameterObject() {
        return new Integer(value);
    }
    

}
