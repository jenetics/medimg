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
 * ImageWriter.java
 *
 * Created on 5. Dezember 2001, 16:16
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

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
    
    protected static Logger logger = Logger.getLogger("org.wewi.medimg.image.io");

    
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
    
    public String toString() {
        return target.toString();    
    } 
}

