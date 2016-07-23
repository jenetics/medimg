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
