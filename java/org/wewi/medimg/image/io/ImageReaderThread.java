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
 * ImageReaderThread.java
 *
 * Created on 24. Januar 2002, 13:44
 */

package org.wewi.medimg.image.io;

import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ImageReaderThread extends Thread {
    private Vector listeners;
    private ImageReader imageReader;
    
    private Component component = null;

    public ImageReaderThread(ImageReader imageReader) {
        this.imageReader = imageReader;
        listeners = new Vector();
    }
    
    public ImageReaderThread(ImageReader imageReader, Component component) {
        this(imageReader); 
        this.component = component;   
    }
    
    public synchronized void addReaderThreadListener(ReaderThreadListener listener) {
        listeners.add(listener);
    }
    
    public synchronized void removeReaderThreadListener(ReaderThreadListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners(ReaderThreadEvent event) {
        Vector rtl;
        synchronized (listeners) {
            rtl = (Vector)listeners.clone();
        }
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
            
            if (component != null) {
                JOptionPane.showMessageDialog(component, "Kann Datei: \"" + 
                                           imageReader.getSource().toString() + 
                                                         "\" nicht öffnen\n" +
                                                         ioe.toString(), 
                                                 "Fehler", JOptionPane.ERROR_MESSAGE);                
            }
            
        }
        
        notifyListeners(event);
    }
    
}
