/*
 * Wizard.java
 *
 * Created on 6. April 2002, 19:37
 */

package org.wewi.medimg.viewer.wizard;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JInternalFrame;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Wizard extends JInternalFrame {
    private Vector listeners = new Vector();
    
    protected Logger logger;
    
    /** Creates a new instance of Wizard */
    public Wizard() {
        super();
        init();
    }
    
    public Wizard(String name) {
        super(name);
        init();
    }
    
    public Wizard(String name, boolean resizeable) {
        super(name, resizeable);
        init();
    }
    
    public Wizard(String name, boolean resizeable, boolean closable) {
        super(name, resizeable, closable);
        init();
    }
    
    public Wizard(String name, boolean resizeable, boolean closable, boolean maximizable) {
        super(name, resizeable, closable, maximizable);   
        init(); 
    }
    
    public Wizard(String name, boolean resizeable, boolean closable, boolean maximizable, boolean iconable) {
        super(name, resizeable, closable, maximizable, iconable);
        init();
    }
    
    private void init() {
        logger = Logger.getLogger(getClass().getPackage().getName());    
    }
    
    public void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public void removeLoggerHandler(Handler handler) {
        logger.removeHandler(handler);
    }
    
    public void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public Level getLoggerLevel() {
        return logger.getLevel();
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
