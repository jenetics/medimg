/**
 * Created on 08.10.2002 10:36:36
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.wewi.medimg.util.param.Parameter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Task {   
    private int id;
    private int iterations;
    private Validator validator;
    private List parameterList;

	/**
	 * Constructor for Task.
	 */
	public Task() {
		super();
        id = 0;
        iterations = 1;
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
        task.setAttribute("iterations.done", Integer.toString(0));
        
        task.addContent(validator.createValidatorElement());
        
        for (Iterator it = parameterList.iterator(); it.hasNext();) {
            Parameter parameter = (Parameter)it.next(); 
            task.addContent(parameter.createParameterElement());   
        }
    
        return task;    
    }    
    
    
    public void initTask(Element task) {
        
    }

}













