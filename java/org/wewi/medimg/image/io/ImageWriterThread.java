/*
 * ImageReaderWriterThread.java
 *
 * Created on 24. Januar 2002, 11:52
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageWriterThread extends Thread {
    private Vector listeners;
    
    private Image image;
    private ImageWriterFactory imageWriterFactory;
    private File targetFileName;
    
    public ImageWriterThread(Image image, ImageWriterFactory imageWriterFactory, File targetFileName) {
        this.image = image;
        this.imageWriterFactory = imageWriterFactory;
        this.targetFileName = targetFileName;
        
        listeners = new Vector();
    }
    
    public void addWriterThreadListener(WriterThreadListener listener) {
        listeners.add(listener);
    }
    
    public void removeWriterThreadListener(WriterThreadListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners(WriterThreadEvent event) {
        Vector wtl = (Vector)listeners.clone();
        WriterThreadListener l;
        for (Iterator it = wtl.iterator(); it.hasNext();) {
            l = (WriterThreadListener)it.next();
            l.imageWritten(event);
        }
    }

    public void run() {
        ImageWriter imageWriter = imageWriterFactory.createImageWriter(image, targetFileName);
        WriterThreadEvent event = new WriterThreadEvent(this);
        try {
            imageWriter.write();
        } catch (ImageIOException ioe) {
            System.err.println("ImageWriterThread.run: " + ioe);
            event.setException(ioe);
        }
        
        notifyListeners(event);      
    }
    
}
