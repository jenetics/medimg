/**
 * Created on 22.10.2002 16:47:03
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class NormalizeFilter extends ImageFilter {
    private int minColor;
    private int maxColor;

	/**
	 * Constructor for NormalizeFilter.
	 * @param image
	 */
	public NormalizeFilter(Image image, int minColor, int maxColor) {
		super(image);
        this.minColor = minColor;
        this.maxColor = maxColor;
	}

	/**
	 * Constructor for NormalizeFilter.
	 * @param component
	 */
	public NormalizeFilter(ImageFilter component, int minColor, int maxColor) {
		super(component);
        this.minColor = minColor;
        this.maxColor = maxColor;        
	}
    

	/**
	 * @see org.wewi.medimg.image.filter.ImageFilter#filter()
	 */
	public void filter() {
        super.filter();
        
        //Feststellen der minimalen und maximalen Farbe
        int min = image.getColor(0);
        int max = image.getColor(0);
        int color;
        for (int i = 1, n = image.getNVoxels(); i < n; i++) {
            color = image.getColor(i);
            if (color < min) {
                min = color;    
            } else if (color > max) {
                max = color;    
            }
        }
        
        //Berechnen der Parameter der Geradengleichung y = k*x + d
        double k = (double)(maxColor - minColor) / (double)(max - min);
        double d = (double)minColor - k*min;
        
        //Normalisieren des Bildes
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.setColor(i, (int)(image.getColor(i)*k + d));            
        }
    
	}

}






