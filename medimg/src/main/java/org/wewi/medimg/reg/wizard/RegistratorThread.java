/*
 * RegistrateThread.java
 *
 * Created on 19. April 2002, 13:17
 */

package org.wewi.medimg.reg.wizard;

import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.reg.Registrator;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class RegistratorThread extends Thread {
    private Registrator reg;
    private Image sourceImage;
    private Image targetImage;  
    private Transformation transformation;  
    private Vector observer;
    
    /** Creates a new instance of RegistrateThread */
    public RegistratorThread(Registrator reg) {
        this.reg = reg;
        sourceImage = new NullImage();
        targetImage = new NullImage();
        observer = new Vector();
    }

    public void addRegistratorListener(RegistratorListener o) {
        observer.add(o);
    }
    
    public void removeRegistratorListener(RegistratorListener o) {
        observer.remove(o);
    }
    
    private void notifyRegistratorFinished(RegistratorEvent event) {
        Vector o = (Vector)observer.clone();
        RegistratorListener so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (RegistratorListener)it.next();
            so.registratorFinished(event);
        }
    }
    
    private void notifyRegistratorStarted(RegistratorEvent event) {
        Vector o = (Vector)observer.clone();
        RegistratorListener so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (RegistratorListener)it.next();
            so.registratorStarted(event);
        }
    } 
        
    public Registrator getRegistrate() {
        return reg;
    }
    
    public void run() {
        notifyRegistratorStarted(new RegistratorEvent(this));
        transformation = (Transformation)reg.registrate(sourceImage, targetImage);
        notifyRegistratorFinished(new RegistratorEvent(this));
    }
    
    public void setImage(Image sourceImage, Image targetImage) {
        this.sourceImage = sourceImage;
        this.targetImage = targetImage;                
    }
    
    public Transformation getTransformation() {
        return transformation;    
    }        
    
}
