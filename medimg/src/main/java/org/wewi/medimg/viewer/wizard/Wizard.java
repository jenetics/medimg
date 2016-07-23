/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
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

import org.wewi.medimg.viewer.Viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Wizard extends JInternalFrame {
    private Vector listeners = new Vector();
    
    protected Logger logger;
    
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
        logger = Logger.getLogger(Viewer.class.getName());    
    }
    
    protected Logger getLogger() {
        return logger;
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
        Vector wl;
        synchronized (listeners) {
            wl = (Vector)listeners.clone();
        }
        WizardListener listener;
        for (Iterator it = wl.iterator(); it.hasNext();) {
            listener = (WizardListener)it.next();
            listener.wizardEventOccurred(wizardEvent);
        }
    }
    
    
    public abstract String getMenuName();
    
    
}
