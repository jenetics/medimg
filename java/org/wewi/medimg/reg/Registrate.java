/*
 * Registrate.java
 *
 * Created on 28. M�rz 2002, 15:42
 */

package org.wewi.medimg.reg;


import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.reg.wizard.RegistrationEvent;
import org.wewi.medimg.reg.wizard.RegistrationListener;
import org.wewi.medimg.util.Timer;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class Registrate {

    
    private Registrator strategy;

    private Transformation trans;

    private RegisterParameter parameter;
    
    private Vector listeners;

    /**
     * Erzeugen eines neuen Register- Objektes. Die Strategie der
     * Registrierung wird als Parameter mitgegeben.
     *
     * @param strategy Registrierungsstrategie
     */
    public Registrate(Registrator s, RegisterParameter p) {
        strategy = s;
        parameter = p; 
        this.trans = null;
        listeners = new Vector();
    }

    /**
     * Berechnung der Registrierung. Die Transformation wird im RegisterParameter-
     * Objekt durchgef�hrt. D.h. Das RegisterParameter- Objekt mu� vorher "gef�llt"
     * sein; das Ergebnis wird ebenfalls im RegisterParameter- Objekt
     * vorgenommen.
     *
     * @throws RegisterException, wenn keine passende Transformation
     *         gefunden werden kann, bzw. Wenn eine der Vorbedingungen
     *         f�r diesen Methodenaufruf nicht vorhanden sind.
     */
    public void calculate() throws RegistrationException {
        notifyRegistrationStarted(new RegistrationEvent(this));
        Timer timer1 = new Timer("CRegister::calculate");
        timer1.start();
        try {
                this.trans = strategy.calculate(parameter);
            } catch (RegistrationException re) {
                throw new RegistrationException();
            }
        timer1.stop();
        timer1.print(); 
        Timer timer2 = new Timer("CRegister::transform");
        timer2.start();
        parameter.setTargetImage(trans.transform(parameter.getSourceImage()));
        timer2.stop();
        timer2.print(); 
        notifyRegistrationFinished(new RegistrationEvent(this));
    }

    //class CRegisterParameter;

    public void setParameter(RegisterParameter p) {
        parameter = p;
    }

    public RegisterParameter getParameter() {
        return parameter; 
    }

    public Registrator getStrategy() {
        return strategy;
    }

    public void setTrans(Transformation trans) {
        this.trans = trans;
    }

    public Transformation getTrans() {
        return trans;
    }    
    
    public void addRegistrationListener(RegistrationListener listener) {
        listeners.add(listener);
    }
    
   
    public void removeRegistrationListener(RegistrationListener listener) {
        listeners.remove(listener);
    }

    protected void notifyRegistrationStarted(RegistrationEvent event) {
        Vector lv = (Vector)listeners.clone();
        RegistrationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (RegistrationListener)it.next();
            l.registrationStarted(event);
        }
    }    
    
    protected void notifyRegistrationFinished(RegistrationEvent event) {
        Vector lv = (Vector)listeners.clone();
        RegistrationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (RegistrationListener)it.next();
            l.registrationFinished(event);
        }
    }    

}
