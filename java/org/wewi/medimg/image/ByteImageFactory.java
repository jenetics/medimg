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
public class ByteImageFactory implements ImageFactory, Singleton {

    private static ByteImageFactory singleton = null;

    private ByteImageFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zurück
     */
    public static synchronized ByteImageFactory getInstance() {
        if (singleton == null) {
            singleton = new ByteImageFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ByteImage(maxX, maxY, maxZ);
    }
    
    
    /**
     * Erzeugen eines neuen ImageData
     */    
    public synchronized Image createImage(Dimension dim) {
        return new ByteImage(dim);    
    }

}
