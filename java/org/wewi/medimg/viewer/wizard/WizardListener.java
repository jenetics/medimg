/*
 * WizardListener.java
 *
 * Created on 6. April 2002, 23:00
 */

package org.wewi.medimg.viewer.wizard;

import java.util.EventListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface WizardListener extends EventListener {
    public void wizardEventOccurred(WizardEvent wizardEvent);  
}
