/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ShortImageDataFactory implements ImageFactory, Singleton {
    
    private static ShortImageDataFactory singleton = null;

    private ShortImageDataFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zurück
     */
    public static synchronized ShortImageDataFactory getInstance() {
        if (singleton == null) {
            singleton = new ShortImageDataFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ShortImageData(maxX, maxY, maxZ);
    }

}
