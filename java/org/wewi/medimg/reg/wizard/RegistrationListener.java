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
public interface RegistrationListener extends EventListener {
    public void registrationStarted(RegistrationEvent event);
    
    public void registrationFinished(RegistrationEvent event);
    
    //public void iterationFinished(RegistrationEvent event);
}
