/**
 * DoubleImage.java
 * Created on 14.06.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleImage extends AbstractImage {

    /**
     * 
     */
    DoubleImage() {
        super();
    }

    /**
     * @param id
     */
    DoubleImage(DoubleImage id) {
        super(id);
    }

    /**
     * @param dim
     */
    public DoubleImage(Dimension dim) {
        super(dim);
    }

    /**
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param minZ
     * @param maxZ
     */
    public DoubleImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     */
    public DoubleImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * @see org.wewi.medimg.image.AbstractImage#createImageData(int)
     */
    protected ImageData createImageData(int size) {
        return null;
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new DoubleImage(this);
    }

}
