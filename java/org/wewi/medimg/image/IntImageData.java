/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class IntImageData extends AbstractImage {
    
    IntImageData() {
        super();
    }     
     
    IntImageData(IntImageData id) {
        super(id);
    }
    
    public IntImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    public IntImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
    }
       
    
    public Object clone() {
        return new IntImageData(this);
    } 
       
	/**
	 * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
	 */
	protected DiscreteData createDiscreteData(int size) {
		return new IntData(size);
	}

}
