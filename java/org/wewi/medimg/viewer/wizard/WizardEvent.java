/*
 * WizardEvent.java
 *
 * Created on 6. April 2002, 23:02
 */

package org.wewi.medimg.viewer.wizard;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class WizardEvent extends EventObject {
    private WizardEventCommand command;
    
    /** Creates a new instance of WizardEvent */
    public WizardEvent(Object source) {
        super(source);
    }
    
    public WizardEvent(Object source, WizardEventCommand command) {
        super(source);
        this.command = command;
    }
    
    public void execute() {
        if (command != null) {
            command.execute();
        }
    }
}
