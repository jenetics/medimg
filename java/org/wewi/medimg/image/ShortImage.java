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
public final class ShortImage extends AbstractImage {   
     
    ShortImage() {
        super();
    }     
     
    ShortImage(ShortImage id) {
        super(id);
    }
    
    public ShortImage(Dimension dim) {
        super(dim);    
    }
    
    public ShortImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public ShortImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }    
     
    protected ImageData createImageData(int size) {
        return new ShortData(size);
    }    
    
    public Object clone() {
        return new ShortImage(this);
    }    


}
