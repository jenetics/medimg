/*
 * RegistrationException.java
 *
 * Created on 27. März 2002, 14:47
 */

package org.wewi.medimg.reg;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class RegistrationException extends java.lang.Exception {

    /**
     * Creates new <code>RegistrationException</code> without detail message.
     */
    public RegistrationException() {
    }


    /**
     * Constructs an <code>RegistrationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RegistrationException(String msg) {
        super(msg);
    }
}


