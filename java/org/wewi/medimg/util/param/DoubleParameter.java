/**
 * Created on 07.10.2002 19:09:51
 *
 */
package org.wewi.medimg.util.param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleParameter implements Parameter {
    private double value;
    private String name;

	/**
	 * Constructor for DoubleParameter.
	 */
	public DoubleParameter(String name, double value) {
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
		return name;
	}
    
    /**
     * @see org.wewi.medimg.util.param.Parameter#setName(String)
     */
    public void setName(String name) {
        this.name = name;
    }    
    
    /**
     * @see org.wewi.medimg.util.param.Parameter#getClassName()
     */
    public String getClassName() {
        return Double.class.getName();
    } 
    
    /**
     * @see org.wewi.medimg.util.param.Parameter#setClassName(String)
     */
    public String setClassName(String clazz) {
        return null;
    }       

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getValue()
	 */
	public String getValue() {
		return Double.toString(value);
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#setValue(String)
	 */
	public void setValue(String val) {
        value = Double.parseDouble(val);
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Parameter#getInstance()
	 */
	public Object getInstance() {
		return new Double(value);
	}

}
