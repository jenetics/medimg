/*
 * ImageReaderThread.java
 *
 * Created on 24. Januar 2002, 13:44
 */

package org.wewi.medimg.image.io;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ImageReaderThread extends Thread {
    private Vector listeners;
    private ImageReader imageReader;

    public ImageReaderThread(ImageReader imageReader) {
        this.imageReader = imageReader;
        listeners = new Vector();
    }
    
    public void addReaderThreadListener(ReaderThreadListener listener) {
        listeners.add(listener);
    }
    
    public void removeReaderThreadListener(ReaderThreadListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners(ReaderThreadEvent event) {
        Vector rtl = (Vector)listeners.clone();
        ReaderThreadListener l;
        for (Iterator it = rtl.iterator(); it.hasNext();) {
            l = (ReaderThreadListener)it.next();
            l.imageRead(event);
        }
    }
    
    public ImageReader getImageReader() {
        return imageReader;
    }

    public void run() {
        ReaderThreadEvent event = new ReaderThreadEvent(this);
        try {
            imageReader.read();
        } catch (ImageIOException ioe) {
            event.setException(new ImageIOException(ioe));
        }
        
        notifyListeners(event);
    }
    
}
