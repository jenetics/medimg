/**
 * Created on 08.10.2002 10:40:54
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.List;

import org.jdom.Element;

/**
 * @author Franz Wilhelmstötter
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
