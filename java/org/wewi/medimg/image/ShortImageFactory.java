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
public class ShortImageFactory implements ImageFactory, Singleton {
    
    private static ShortImageFactory singleton = null;

    private ShortImageFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zur�ck
     */
    public static synchronized ShortImageFactory getInstance() {
        if (singleton == null) {
            singleton = new ShortImageFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ShortImage(maxX, maxY, maxZ);
    }
    
    public synchronized Image createImage(Dimension dim) {
        return new ShortImage(dim);    
    }

}
