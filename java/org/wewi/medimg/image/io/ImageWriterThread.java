/*
 * ImageReaderWriterThread.java
 *
 * Created on 24. Januar 2002, 11:52
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;

import java.util.Observable;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageWriterThread extends Observable implements Runnable {
    private Image image;
    private ImageWriterFactory imageWriterFactory;
    private File targetFileName;
    
    public ImageWriterThread(Image image, ImageWriterFactory imageWriterFactory, File targetFileName) {
        this.image = image;
        this.imageWriterFactory = imageWriterFactory;
        this.targetFileName = targetFileName;
    }

    public void run() {
        ImageWriter imageWriter = imageWriterFactory.createImageWriter(image, targetFileName);
        try {
            imageWriter.write();
        } catch (IOException ioe) {
            System.out.println("ImageWriterThread.run: " + ioe);
        }
        
        setChanged();
        notifyObservers();
        clearChanged();       
    }
    
}
