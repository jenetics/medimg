/**
 * LongImage.java
 * Created on 14.06.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class LongImage extends AbstractImage {

    /**
     * 
     */
    LongImage() {
        super();
    }

    /**
     * @param id
     */
    LongImage(LongImage id) {
        super(id);
    }

    /**
     * @param dim
     */
    public LongImage(Dimension dim) {
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
    public LongImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     */
    public LongImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * @see org.wewi.medimg.image.AbstractImage#createImageData(int)
     */
    protected ImageData createImageData(int size) {
        return new LongData(size);
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new LongImage(this);
    }

}
