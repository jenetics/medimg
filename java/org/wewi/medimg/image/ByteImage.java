/**
 * ByteImage.java
 *
 * Created on 10. Mai 2002, 15:59
 */

package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ByteImage extends AbstractImage {
    private byte[] data;   
     
    ByteImage() {
        super();
    }     
     
    ByteImage(ByteImage id) {
        super(id);
    }
    
    public ByteImage(Dimension dim) {
        super(dim);    
    }
    
    public ByteImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ByteImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    } 
    
    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected ImageData createImageData(int size) {
        return new ByteData(size);
    }          
    
    public Object clone() {
        return new ByteImage(this);
    }    

}
