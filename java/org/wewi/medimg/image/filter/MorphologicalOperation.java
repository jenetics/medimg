/**
 * Created on 22.11.2002 12:18:55
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.MarginImage;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class MorphologicalOperation extends ImageFilter {


    protected Image b;
    protected Image marginImage;
    
    
    public MorphologicalOperation(Image image) {
        super(image); 
        
        b = new ImageData(3, 3, 1);
        b.resetColor(0);             
    }

	/**
	 * Constructor for MorphologicalOperation.
	 * @param image
	 */
	public MorphologicalOperation(Image image, Image b) throws IllegalArgumentException {
		super(image);
        
        if (!checkSymetrie(b.getDimension())) {
            throw new IllegalArgumentException("Illegal Image b");    
        }
        
        this.b = b;        
	}
    
    public MorphologicalOperation(ImageFilter component) {
        super(component);  
        
        b = new ImageData(3, 3, 1);
        b.resetColor(0);          
    }

	/**
	 * Constructor for MorphologicalOperation.
	 * @param component
	 */
	public MorphologicalOperation(ImageFilter component, Image b) throws IllegalArgumentException {
		super(component);
        
        if (!checkSymetrie(b.getDimension())) {
            throw new IllegalArgumentException();    
        }        
        
        this.b = b;
	}
    
    private boolean checkSymetrie(Dimension dim) {
        if ((dim.getSizeX() % 2) == 0 || (dim.getSizeX() != dim.getSizeY())) {
            return false;        
        }    
        return true;
    }
    
    protected void imageFiltering() {
        int margin = b.getDimension().getSizeX()/2;
        marginImage = new MarginImage(image, margin);
        int minZ = image.getMinZ();
        
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) {
                image.setColor(i, j, minZ, operation(i, j));           
            }    
        }
        
        
        marginImage = null;      
    }
    
    protected abstract int operation(int i, int j);

}













