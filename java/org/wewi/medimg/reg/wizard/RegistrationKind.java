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
public class RegistrationKind extends Enumeration {
    public static final RegistrationKind PCA = new RegistrationKind();
    public static final RegistrationKind MoreToCome = new RegistrationKind();    
    
    private static int refCount = 0;  
    private RegistrationKind() { 
        super(++refCount);
    }
}
