/**
 * Created on 22.11.2002 12:24:11
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DilationFilter extends MorphologicalOperation {

    public DilationFilter(Image image) {
        super(image);    
    }

	/**
	 * Constructor for DilationFilter.
	 * @param image
	 * @param b
	 */
	public DilationFilter(Image image, Image b) {
		super(image, b);
	}
    
    public DilationFilter(ImageFilter component) {
        super(component);    
    }

	/**
	 * Constructor for DilationFilter.
	 * @param component
	 * @param b
	 */
	public DilationFilter(ImageFilter component, Image b) {
		super(component, b);
	}

	/**
	 * @see org.wewi.medimg.image.filter.MorphologicalOperation#morphologicalFiltering()
	 */
	protected int operation(int i, int j) {
        int result = 0;
        
        
        
        
        Dimension bdim = b.getDimension();
        int margin = bdim.getSizeX()/2;
        int ox = bdim.getMinX()+margin;
        int oy = bdim.getMinY()+margin;
        int minZ = image.getMinZ();

        for (int x = -margin; x <= margin; x++) {
            for (int y = -margin; y <= margin; y++) {
                result = Math.max(result, marginImage.getColor(i+x, j+y, minZ) + 
                                                    b.getColor(x+ox, y+oy, b.getMinZ()));                
            }    
        }
        
        return result;   
	}

}
