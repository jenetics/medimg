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
public class IntImageDataFactory implements ImageFactory, Singleton {

    private static IntImageDataFactory singleton = null;

    private IntImageDataFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zurück
     */
    public static synchronized IntImageDataFactory getInstance() {
        if (singleton == null) {
            singleton = new IntImageDataFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ImageData(maxX, maxY, maxZ);
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */    
    public synchronized Image createImage(Dimension dim) {
        return new ImageData(dim);    
    }
}
