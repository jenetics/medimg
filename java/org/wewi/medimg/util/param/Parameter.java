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
