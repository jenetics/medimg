/**
 * Created on 13.11.2002 09:59:21
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.MarginImage;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ConvolutionFilter extends ImageFilter {
    protected Kernel kernel;

	/**
	 * Constructor for ConvolutionFilter.
	 * @param image
	 */
	public ConvolutionFilter(Image image, Kernel kernel) {
		super(image);
        this.kernel = kernel;
	}

	/**
	 * Constructor for ConvolutionFilter.
	 * @param component
	 */
	public ConvolutionFilter(ImageFilter component, Kernel kernel) {
		super(component);
        this.kernel = kernel;
	}
    
    public void filter() {
        super.filter(); 
        
        
        final int minX = image.getMinX();
        final int maxX = image.getMaxX();
        final int minY = image.getMinY();
        final int maxY = image.getMaxY();
        final int minZ = image.getMinZ();
        Image tempImage = new MarginImage(image, kernel.getMargin());
        KernelMaskSum maskSum = new KernelMaskSum(tempImage, minZ, kernel);
        

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {                
                image.setColor(i, j, minZ, (int)maskSum.getKernelMaskSum(i, j));  
            }
        }   
    }
}




















