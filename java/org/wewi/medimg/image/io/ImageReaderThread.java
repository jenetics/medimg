/*
 * ImageReaderThread.java
 *
 * Created on 24. Januar 2002, 13:44
 */

package org.wewi.medimg.image.io;

import java.util.Observable;

import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 
 */
public final class ImageReaderThread extends Observable implements Runnable {
    private ImageReader imageReader;

    public ImageReaderThread(ImageReader imageReader) {
        this.imageReader = imageReader;
    }
    
    public ImageReader getImageReader() {
        return imageReader;
    }

    public void run() {
        try {
            imageReader.read();
        } catch (IOException ioe) {
            System.out.println("ImageReaderThread.run: " + ioe);
        }
        
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
}
