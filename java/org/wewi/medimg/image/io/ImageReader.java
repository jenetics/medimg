/*
 * ImageReader.java
 *
 * Created on 5. Dezember 2001, 14:45
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.GreyRGBConversion;

import java.io.File;
import java.io.IOException;

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
    
    ImageReader() {
        colorConversion = new GreyRGBConversion();
    }
    
    public ImageReader(ImageFactory imageFactory, File source) {
        this.imageFactory = imageFactory;
        this.source = source;
        colorConversion = new GreyRGBConversion();
        range = new Range(0, Integer.MAX_VALUE);
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
    File getSource() {
        return source;
    }
    
    public Range getRange() {
        return range;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }
    
    /**
     * Setzen der Datei von der gelesen werden soll
     *
     * @param source neue Quelldatei (Quellverzeichnis)
     */
    void setSource(File source) {
        this.source = source;
    }
    
    public void setColorConversion(ColorConversion cc) {
        colorConversion = cc;
    }
    
    public ColorConversion getColorConversion() {
        return colorConversion;
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

