/**
 * Created on 22.10.2002 16:47:03
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.LinearNormalizeFunction;
import org.wewi.medimg.image.ops.MinMaxOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;
import org.wewi.medimg.image.ops.UnaryPointTransformer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class LinearNormalizeFilter extends ImageFilter {
    private int minColor;
    private int maxColor;

	/**
	 * Constructor for LinearNormalizeFilter.
	 * @param image
	 */
	public LinearNormalizeFilter(Image image, int minColor, int maxColor) {
		super(image);
        this.minColor = minColor;
        this.maxColor = maxColor;
	}

	/**
	 * Constructor for LinearNormalizeFilter.
	 * @param component
	 */
	public LinearNormalizeFilter(ImageFilter component, int minColor, int maxColor) {
		super(component);
        this.minColor = minColor;
        this.maxColor = maxColor;        
	}
    

	/**
	 * @see org.wewi.medimg.image.filter.ImageFilter#filter()
	 */
	protected void componentFilter() {
        
        //Feststellen der minimalen und maximalen Farbe
        MinMaxOperator op = new MinMaxOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();
        
        //System.out.println("min: " + op.getMinimum() + ", max: " + op.getMaximum());
        
        //Normalisieren des Bildes
        LinearNormalizeFunction functor = new LinearNormalizeFunction(op.getMinimum(), op.getMaximum(),
                                                                      minColor, maxColor);
        UnaryPointTransformer transformer = new UnaryPointTransformer(image, functor);
        transformer.transform();
    
	}
    
    public String toString() {
        return getClass().getName() + ":\n" +
                "min: " + minColor + ", max: " + maxColor;    
    }

}






