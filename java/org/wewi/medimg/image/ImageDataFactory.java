/**
 * ImageDataFactory.java
 *
 * Created on 18. J�nner 2002, 20:20
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.2
 */
public final class ImageDataFactory implements ImageFactory, Singleton {
    private static ImageDataFactory singleton = null;

    private ImageDataFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zur�ck
     */
    public static synchronized ImageDataFactory getInstance() {
        if (singleton == null) {
            singleton = new ImageDataFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int sizeX, int sizeY, int sizeZ) {
        return new ImageData(sizeX, sizeY, sizeZ);
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */    
    public synchronized Image createImage(Dimension dim) {
        return new ImageData(dim);   
    }
    
}
