/**
 * ImageReaderWriterThread.java
 *
 * Created on 24. Januar 2002, 11:52
 */

package org.wewi.medimg.image.io;

import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageWriterThread extends Thread {
    private Vector listeners;
    
    private ImageWriter imageWriter;
    private Component component;

    
    public ImageWriterThread(ImageWriter imageWriter) {
        this(imageWriter, null);   
    }
    
    public ImageWriterThread(ImageWriter imageWriter, Component component) {
        this.imageWriter = imageWriter;
        this.component = component;
        
        listeners = new Vector();
    }
    
    public synchronized void addWriterThreadListener(WriterThreadListener listener) {
        listeners.add(listener);
    }
    
    public synchronized void removeWriterThreadListener(WriterThreadListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners(WriterThreadEvent event) {
        Vector wtl;
        synchronized (listeners) {
            wtl = (Vector)listeners.clone();
        }
        WriterThreadListener l;
        for (Iterator it = wtl.iterator(); it.hasNext();) {
            l = (WriterThreadListener)it.next();
            l.imageWritten(event);
        }
    }

    public void run() {
        WriterThreadEvent event = new WriterThreadEvent(this);
        try {
            imageWriter.write();
        } catch (ImageIOException ioe) {
            System.err.println("ImageWriterThread.run: " + ioe);
            
            event.setException(ioe); 
            
            if (component != null) {
                JOptionPane.showMessageDialog(component, "Kann Datei: \"" + 
                                           imageWriter.toString() + 
                                                         "\" nicht öffnen\n" +
                                                         ioe.toString(), 
                                                 "Fehler", JOptionPane.ERROR_MESSAGE);                
            }                     
        }
        
        notifyListeners(event);      
    }
    
}
