/*
 * ImageReaderWriterThread.java
 *
 * Created on 24. Januar 2002, 11:52
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.util.Vector;
import java.util.Iterator;

import java.io.File;
import java.io.IOException;

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
        try {
            imageWriter.write();
        } catch (IOException ioe) {
            System.out.println("ImageWriterThread.run: " + ioe);
        }
        
        notifyListeners(new WriterThreadEvent(this));      
    }
    
}
