/**
 * ByteImageData.java
 *
 * Created on 10. Mai 2002, 15:59
 */

package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ByteImageData extends AbstractImage {
    private byte[] data;   
     
    ByteImageData() {
        super();
    }     
     
    ByteImageData(ByteImageData id) {
        super(id);
    }
    
    public ByteImageData(Dimension dim) {
        super(dim);    
    }
    
    public ByteImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ByteImageData(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    } 
    
    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected DiscreteData createDiscreteData(int size) {
        return new ByteData(size);
    }          
    
    public Object clone() {
        return new ByteImageData(this);
    }    

}
