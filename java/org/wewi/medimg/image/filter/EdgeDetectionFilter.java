/**
 * Created on 14.11.2002 07:03:45
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.MarginImage;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 * 
 * @todo Codedoubeling with ConvolutionFilter!
 */
public class EdgeDetectionFilter extends ImageFilter {
    private Kernel horizontalKernel;
    private Kernel verticalKernel;

	/**
	 * Constructor for EdgeDetectionFilter.
	 * @param image
	 */
	public EdgeDetectionFilter(Image image, Kernel horizontal, Kernel vertical) {
		super(image);
        horizontalKernel = horizontal;
        verticalKernel = vertical;
	}

	/**
	 * Constructor for EdgeDetectionFilter.
	 * @param component
	 */
	public EdgeDetectionFilter(ImageFilter component, Kernel horizontal, Kernel vertical) {
		super(component);
        horizontalKernel = horizontal;
        verticalKernel = vertical;        
	}
    
    
    protected void imageFiltering() {
        
        final int minX = image.getMinX();
        final int maxX = image.getMaxX();
        final int minY = image.getMinY();
        final int maxY = image.getMaxY();
        final int minZ = image.getMinZ();
        Image tempImage = new MarginImage(image, verticalKernel.getMargin()); 
        
        int verticalKernelSum = 0;
        int horizontalKernelSum = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                
                //Berechnen der vertikalen Maske
                verticalKernelSum = 0;
                horizontalKernelSum = 0;
                for (int k = -verticalKernel.getMargin(), n = verticalKernel.getMargin(); k <= n; k++) {
                    for (int l = -verticalKernel.getMargin(), m = verticalKernel.getMargin(); l <= m; l++) {
                        verticalKernelSum += verticalKernel.getValue(k, l) * tempImage.getColor(i+k, j+l, minZ); 
                        horizontalKernelSum += horizontalKernel.getValue(k, l) * tempImage.getColor(i+k, j+l, minZ);      
                    } 
                }
                verticalKernelSum = Math.abs((verticalKernelSum/verticalKernel.getDivisor()) + 
                                             verticalKernel.getBias());
                horizontalKernelSum = Math.abs((horizontalKernelSum/horizontalKernel.getDivisor()) + 
                                             horizontalKernel.getBias());                
                 
                image.setColor(i, j, minZ, verticalKernelSum + horizontalKernelSum);   
            }
        }                  
        
    }

}

























