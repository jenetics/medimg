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
 * Created on 08.10.2002 10:40:54
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.List;

import org.jdom.Element;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public abstract class Validator {
    protected List parameterList;

    /**
     * Constructor for Validator.
     */
    public Validator() {
        super();
    }
    
    public void setParameterList(List pl) {
        parameterList = pl;    
    }
    
    public List getParameterList() {
        return parameterList;    
    }
    
    
    public abstract Element createValidatorElement();
    
    public abstract void validate();

}
