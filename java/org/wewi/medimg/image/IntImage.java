/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class IntImage extends AbstractImage {
    
    IntImage() {
        super();
    }     
     
    IntImage(IntImage id) {
        super(id);
    }
    
    public IntImage(Dimension dim) {
        super(dim);    
    }
    
    public IntImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public IntImage(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
    }
       
    
    public Object clone() {
        return new IntImage(this);
    } 
       
    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected ImageData createImageData(int size) {
        return new IntData(size);
    }

}
