/**
 * Created on 22.11.2002 12:25:56
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ErosionFilter extends MorphologicalOperation {

    public ErosionFilter(Image image) {
        super(image);    
    }

    /**
     * Constructor for ErosionFilter.
     * @param image
     * @param b
     */
    public ErosionFilter(Image image, Image b) {
        super(image, b);
    }
    
    public ErosionFilter(ImageFilter component) {
        super(component);    
    }

    /**
     * Constructor for ErosionFilter.
     * @param component
     * @param b
     */
    public ErosionFilter(ImageFilter component, Image b) {
        super(component, b);
    }

    protected int operation(int i, int j) {
        int result = Integer.MAX_VALUE;
        
        Dimension bdim = b.getDimension();
        int margin = bdim.getSizeX()/2;
        int ox = bdim.getMinX()+margin;
        int oy = bdim.getMinY()+margin;
        int minZ = image.getMinZ();

        for (int x = -margin; x <= margin; x++) {
            for (int y = -margin; y <= margin; y++) {
                result = Math.min(result, marginImage.getColor(i-x, j-y, minZ) - 
                                                    b.getColor(x+ox, y+oy, b.getMinZ()));                
            }    
        }
        
        return result;   
    }

}
