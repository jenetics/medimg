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
 * Created on 08.10.2002 10:36:36
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Element;
import org.wewi.medimg.util.param.Parameter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class Task {   
    private int id;
    private int iterations;
    private int iterationsDone;
    private Validator validator;
    private List parameterList;

    /**
     * Constructor for Task.
     */
    public Task() {
        super();
        id = 0;
        iterations = 1;
        iterationsDone = 0;
        parameterList = new ArrayList();
    }
    
    public void setId(int id) {
        this.id = id;    
    }
    
    public void setIterations(int it) {
        iterations = it;    
    }
    
    public void setValidator(Validator val) {
        validator = val;    
    }
    
    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);    
    }
    
    public Element createTaskElement() {
        Element task = new Element("Task");
        task.setAttribute("id", Integer.toString(id));
        task.setAttribute("iterations", Integer.toString(iterations));
        task.setAttribute("iterations.done", Integer.toString(iterationsDone));
        
        task.addContent(validator.createValidatorElement());
        
        for (Iterator it = parameterList.iterator(); it.hasNext();) {
            Parameter parameter = (Parameter)it.next(); 
            task.addContent(parameter.createParameterElement());   
        }
    
        return task;    
    }    
    
    
    public void initTask(Element task) {
        try {
            id = task.getAttribute("id").getIntValue();
            iterations = task.getAttribute("iterations").getIntValue();
            iterationsDone = task.getAttribute("iterations.done").getIntValue();
        } catch (DataConversionException e) {
            System.err.println("Task.initTask(): " + e);
            return;
        }    
        
        Element validatorElement = task.getChild("Validator");
        String validatorName = validatorElement.getAttribute("class").getValue();
        try {
            validator  = (Validator)Class.forName(validatorName).newInstance();
        } catch (InstantiationException e) {
            System.err.println("Task.initTask(): " + e);
            return;            
        } catch (IllegalAccessException e) {
            System.err.println("Task.initTask(): " + e);
            return;            
        } catch (ClassNotFoundException e) {
            System.err.println("Task.initTask(): " + e);
            return;            
        }
        
        List param = task.getChildren("Parameter");
        for (Iterator it = param.iterator(); it.hasNext();) {
            Element paramElement = (Element)it.next();
            String paramName = paramElement.getAttribute("class").getValue();
            Parameter p = null;
            try {
                p = (Parameter)Class.forName(paramName).newInstance();
            } catch (InstantiationException e) {
                System.err.println("Task.initTask(): " + e);
                return;                 
            } catch (IllegalAccessException e) {
                System.err.println("Task.initTask(): " + e);
                return;       
            } catch (ClassNotFoundException e) {
                System.err.println("Task.initTask(): " + e);
                return;                       
            }
            
            p.initParameter(paramElement);
            parameterList.add(p);   
        }
        
        validator.setParameterList(parameterList);
    }
    
    public void execute() {
        for (int i = iterationsDone; i < iterations; i++) {
            validator.validate();
            iterationsDone = i;   
        }    
    }

}













