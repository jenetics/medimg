/**
 * Created on 07.10.2002 19:19:40
 *
 */
package org.wewi.medimg.util.param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class StringParameter implements Parameter {
    private String value;
    private String name;

	/**
	 * Constructor for StringParameter.
	 */
	public StringParameter(String name, String value) {
		this.value = value;
        this.name = name;
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#setParameter(Parameter)
	 */
	public void setParameter(Parameter parameter) {
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#setParameter(List)
	 */
	public void setParameter(List parameterList) {
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getParameterList()
	 */
	public List getParameterList() {
		return new ArrayList();
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getName()
	 */
	public String getName() {
		return String.class.getName();
	}
    
    /**
     * @see org.wewi.medimg.util.param.Parameter#getClassName()
     */
    public String getClassName() {
        return String.class.getName();
    }    

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getValue()
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#setValue(String)
	 */
	public void setValue(String val) {
        value = val;
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getInstance()
	 */
	public Object getInstance() {
		return value;
	}

	/**
	 * @see org.wewi.medimg.util.param.Parameter#setName(String)
	 */
	public void setName(String name) {
	}

}
