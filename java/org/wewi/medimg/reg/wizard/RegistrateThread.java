/*
 * RegistrateThread.java
 *
 * Created on 19. April 2002, 13:17
 */

package org.wewi.medimg.reg.wizard;

import org.wewi.medimg.reg.Registrate;
import org.wewi.medimg.reg.RegistrationException;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class RegistrateThread extends Thread {
    private Registrate reg;
    
    /** Creates a new instance of RegistrateThread */
    public RegistrateThread(Registrate reg) {
        this.reg = reg;
    }
    
    public Registrate getRegistrate() {
        return reg;
    }
    
    public void run() {
        try {
            reg.calculate();
        } catch (RegistrationException re) {
            re.printStackTrace();
        }
        
    }
    
}
