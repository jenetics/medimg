/*
 * ImageWriter.java
 *
 * Created on 5. Dezember 2001, 16:16
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ImageWriter {
    protected Image image;
    protected File target;
    protected String imageExtention = "";
    
    private Vector listeners;
    
    ImageWriter() {
        listeners = new Vector();
    }
    
    public ImageWriter(Image image, File target) {
        this.image = image;
        this.target = target;
        listeners = new Vector();
    }
   
    
    public synchronized void addProgressListener(ImageIOProgressListener l) {
        listeners.add(l);
    }
    
    public synchronized void removeProgressListener(ImageIOProgressListener l) {
        listeners.remove(l);
    }
    
    protected void notifyProgressListener(ImageIOProgressEvent event) {
        Vector list = (Vector)listeners.clone();
        ImageIOProgressListener l;
        for (Iterator it = list.iterator(); it.hasNext();) {
            l = (ImageIOProgressListener)it.next();
            l.progressChanged(event);
        }
    }    

    
    public abstract void write() throws ImageIOException;   
}

