/**
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
    
    private Vector listeners;

    
    public ImageWriter(Image image, String target) {
        this(image, new File(target));    
    }
    
    public ImageWriter(Image image, File target) {
        this.image = image;
        this.target = target;
        
        init();
    }
    
    private void init() {
        listeners = new Vector();    
    }
   
    
    public synchronized void addProgressListener(ImageIOProgressListener l) {
        listeners.add(l);
    }
    
    public synchronized void removeProgressListener(ImageIOProgressListener l) {
        listeners.remove(l);
    }
    
    protected void notifyProgressListener(ImageIOProgressEvent event) {
        Vector list;
        synchronized (listeners) {
            list = (Vector)listeners.clone();
        }
        ImageIOProgressListener l;
        for (Iterator it = list.iterator(); it.hasNext();) {
            l = (ImageIOProgressListener)it.next();
            l.progressChanged(event);
        }
    }    

    
    public abstract void write() throws ImageIOException;   
}

