/*
 * RegistrationKind.java
 *
 * Created on 7. April 2002, 14:28
 */

package org.wewi.medimg.reg.wizard;

import org.wewi.medimg.util.Enumeration;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */

public class RegistratorEnumeration extends Enumeration {
    public static final RegistratorEnumeration PCA_METHOD = 
                      new RegistratorEnumeration("Principal Component Method");
    public static final RegistratorEnumeration MC_METHOD = 
                      new RegistratorEnumeration("Monte Carlo Method");

	public static final RegistratorEnumeration[] ENUMERATION = 
	                          {PCA_METHOD, MC_METHOD};
    
    private String name;
    private static int refCount = 0;  
    private RegistratorEnumeration(String name) { 
        super(++refCount);
        this.name = name;
    }
    
    public String toString() {
    	return name;	
    }
}
