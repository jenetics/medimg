/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ShortImageData extends AbstractImage {   
     
    ShortImageData() {
        super();
    }     
     
    ShortImageData(ShortImageData id) {
        super(id);
    }
    
    public ShortImageData(Dimension dim) {
        super(dim);    
    }
    
    public ShortImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ShortImageData(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }    
     
   
    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected DiscreteData createDiscreteData(int size) {
        return new ShortData(size);
    }    
    
    public Object clone() {
        return new ShortImageData(this);
    }    


}
