/**
 * FloatImage.java
 * Created on 14.06.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class FloatImage extends AbstractImage {

    /**
     * 
     */
    FloatImage() {
        super();
    }

    /**
     * @param id
     */
    FloatImage(FloatImage id) {
        super(id);
    }

    /**
     * @param dim
     */
    public FloatImage(Dimension dim) {
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
    public FloatImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     */
    public FloatImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * @see org.wewi.medimg.image.AbstractImage#createImageData(int)
     */
    protected ImageData createImageData(int size) {
        return new FloatData(size);
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new FloatImage(this);
    }

}
