/*
 * RegistrationListener.java
 *
 * Created on 19. April 2002, 15:45
 */

package org.wewi.medimg.reg.wizard;

import java.util.EventListener;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public interface RegistratorListener extends EventListener {
	
    public void registratorStarted(RegistratorEvent event);
    
    public void registratorFinished(RegistratorEvent event);
    
    //public void iterationFinished(RegistrationEvent event);
}
