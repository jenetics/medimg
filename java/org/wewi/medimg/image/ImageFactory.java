/**
 * ImageFactory.java
 *
 * Created on 18. Jänner 2002, 19:50
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageFactory {
    public Image createImage(int sizeX, int sizeY, int sizeZ);
    
    public Image createImage(Dimension dim);
}

