/*
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
    protected ImageFactory imageFactory;
    protected File source;
    protected Range range;
    protected ColorConversion colorConversion;
    
    private Vector listeners;
    
    ImageReader() {
        colorConversion = new GreyColorConversion();
        listeners = new Vector();
    }
    
    public ImageReader(ImageFactory imageFactory, File source) {
        this.imageFactory = imageFactory;
        this.source = source;
        colorConversion = new GreyColorConversion();
        range = new Range(0, Integer.MAX_VALUE);
        listeners = new Vector();
    }
    
    public ImageReader(ImageFactory imageFactory, File source, Range range) {
        this(imageFactory, source);
        this.range = range;
    }
    
    /**
     * Liefert die Quelle von der gelesen werden soll
     *
     * @return Datei von der das Bild gelesen wird.
     */
    public File getSource() {
        return source;
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
    
    public Range getRange() {
        return range;
    }
    
    public abstract int getSlices() throws ImageIOException;
    
    public void setRange(Range range) {
        this.range = range;
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
    public abstract Image getImage();
}

