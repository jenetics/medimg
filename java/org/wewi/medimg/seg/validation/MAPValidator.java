/**
 * Created on 08.10.2002 10:51:30
 *
 */
package org.wewi.medimg.seg.validation;

import org.jdom.Element;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPValidator extends Validator {

	/**
	 * Constructor for MAPValidator.
	 */
	public MAPValidator() {
		super();
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Validator#createValidatorElement()
	 */
	public Element createValidatorElement() {
	    Element e = new Element("Validator");
        e.setAttribute("class", getClass().getName());
       	
        return e;
	}

	/**
	 * @see org.wewi.medimg.seg.validation.Validator#validate()
	 */
	public void validate() {
	}

}
