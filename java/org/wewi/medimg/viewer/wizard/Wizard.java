/*
 * Wizard.java
 *
 * Created on 6. April 2002, 19:37
 */

package org.wewi.medimg.viewer.wizard;

import java.util.Vector;
import java.util.Iterator;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Wizard extends JInternalFrame {
    private Vector listeners = new Vector();
    
    /** Creates a new instance of Wizard */
    public Wizard() {
        super();
    }
    
    public Wizard(String name) {
        super(name);
    }
    
    public Wizard(String name, boolean resizeable) {
        super(name, resizeable);
    }
    
    public Wizard(String name, boolean resizeable, boolean closable) {
        super(name, resizeable, closable);
    }
    
    public Wizard(String name, boolean resizeable, boolean closable, boolean maximizable) {
        super(name, resizeable, closable, maximizable);    
    }
    
    public Wizard(String name, boolean resizeable, boolean closable, boolean maximizable, boolean iconable) {
        super(name, resizeable, closable, maximizable, iconable);
    }
    
    public synchronized void addWizardListener(WizardListener listener) {
        listeners.add(listener);
    }
    
    public synchronized void removeWizardListener(WizardListener listener) {
        listeners.remove(listener);
    }
    
    public void deleteWizardListeners() {
        listeners.removeAllElements();
    }
    
    protected void notifyWizardListeners(WizardEvent wizardEvent) {
        WizardListener listener;
        Vector wl = (Vector)listeners.clone();
        for (Iterator it = wl.iterator(); it.hasNext();) {
            listener = (WizardListener)it.next();
            listener.wizardEventOccurred(wizardEvent);
        }
    }
    
    public abstract String getMenuName();
    
    
}
