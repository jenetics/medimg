/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class ByteImageDataFactory implements ImageFactory, Singleton {

    private static ByteImageDataFactory singleton = null;

    private ByteImageDataFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zur�ck
     */
    public static synchronized ByteImageDataFactory getInstance() {
        if (singleton == null) {
            singleton = new ByteImageDataFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ByteImageData(maxX, maxY, maxZ);
    }
    
    
    /**
     * Erzeugen eines neuen ImageData
     */    
    public synchronized Image createImage(Dimension dim) {
        return new ByteImageData(dim);    
    }

}
