package org.wewi.medimg.reg.wizard;


import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.wewi.medimg.alg.ObservableAlgorithm;
import org.wewi.medimg.reg.Registrator;


/**
 * @author werner weiser
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class ObservableRegistrator extends ObservableAlgorithm 
                                            implements Registrator { 

    private Vector registratorListener;
    protected Logger logger;
    
    public ObservableRegistrator() {
        registratorListener = new Vector();
        logger = Logger.getLogger(ObservableRegistrator.class.getName());        
    }
    
    public synchronized void addLoggerHandler(Handler handler) {
        //Es soll kein Handler doppelt eingefügt werden.
        Handler[] handlers = logger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            if (handlers[i] == handler) {
                return;
            }
        }
        
        logger.addHandler(handler);
    }
    
    public synchronized void removeLoggerHandler(Handler handler) {
        logger.removeHandler(handler);
    }
    
    public synchronized void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public Level getLoggerLevel() {
        return logger.getLevel();
    }
    
    public synchronized void addRegistratorListener(RegistratorListener o) {
        registratorListener.add(o);
    }
    
    public synchronized void removeRegistratorListener(RegistratorListener o) {
        registratorListener.remove(o);
    }
    
    protected void notifyRegistratorStarted(RegistratorEvent event) {
        Vector lv;
        synchronized (registratorListener) {
            lv = (Vector)registratorListener.clone();
        }
        RegistratorListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (RegistratorListener)it.next();
            l.registratorStarted(event);
        }
    }    
    
    protected void notifyRegistratorFinished(RegistratorEvent event) {
        Vector lv;
        synchronized (registratorListener) {
            lv = (Vector)registratorListener.clone();
        }
        RegistratorListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (RegistratorListener)it.next();
            l.registratorFinished(event);
        }
    }  
    
    public abstract String getRegistratorName();  
    
    public abstract String toString(); 
    
}