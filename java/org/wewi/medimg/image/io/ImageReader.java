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
 * ImageReader.java
 *
 * Created on 5. Dezember 2001, 14:45
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.GreyColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 *
 */
public abstract class ImageReader {
    protected File source;
    protected Range range;
    protected Image image;
    protected ImageFactory imageFactory;
    protected ColorConversion colorConversion;
    
    private Vector listeners;
    
    
    public ImageReader(ImageFactory imageFactory, String source) {
        this(imageFactory, new File(source));    
    }
    
    public ImageReader(ImageFactory imageFactory, File source) {
        this(imageFactory, source, new Range(0, Integer.MAX_VALUE));
    }
    
    public ImageReader(ImageFactory imageFactory, String source, Range range) {
        this(imageFactory, new File(source), range);    
    }
    
    public ImageReader(ImageFactory imageFactory, File source, Range range) {
        this.imageFactory = imageFactory;
        this.source = source;
        this.range = range;
        
        init();
    }
    
    private void init() {
        colorConversion = new GreyColorConversion();
        listeners = new Vector();        
    }
    
    /**
     * Returns the colorConversion.
     * @return ColorConversion
     */
    public ColorConversion getColorConversion() {
        return colorConversion;
    }

    /**
     * Sets the colorConversion.
     * @param colorConversion The colorConversion to set
     */
    public void setColorConversion(ColorConversion colorConversion) {
        this.colorConversion = colorConversion;
    }    
    
    /**
     * Liefert die Quelle von der gelesen werden soll
     *
     * @return Datei von der das Bild gelesen wird.
     */
    public File getSource() {
        return source;
    }
    
    public Range getRange() {
        return range;
    }
    
    /**
     * Starten des Lesevorgangs
     */
    public abstract void read() throws ImageIOException;
    
    /**
     * Liefert das vom Reader gelesene Image
     *
     * @return gelesene Image
     */
    public Image getImage() {
        return image;    
    }   
    
    /**
     * Hinzufügen eines ProgressListners. Diese Methode ist synchronisiert.
     * 
     * @param l ImageIOProgressListener, der hinzugefügt werden soll
     */
    public synchronized void addProgressListener(ImageIOProgressListener l) {
        listeners.add(l);
    }
    
    /**
     * Entfernen eines ProgressListeners.
     * Diese Methode ist synchronisiert.
     */
    public synchronized void removeProgressListener(ImageIOProgressListener l) {
        listeners.remove(l);
    }
    
    /**
     * Informieren der ProgressListener über einen Lesefortschritt
     */
    protected void notifyProgressListener(ImageIOProgressEvent event) {
        Vector list;
        synchronized (listeners) {
            list = (Vector)listeners.clone();
        }
        ImageIOProgressListener l;
        for (Iterator it = list.iterator(); it.hasNext();) {
            l = (ImageIOProgressListener)it.next();
            if (l != null) {
                l.progressChanged(event);
            }
        }
    }
    
    public String toString() {
        return getSource().toString();    
    }

}

