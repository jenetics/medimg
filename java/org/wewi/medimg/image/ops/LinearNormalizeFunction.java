/**
 * Created on 14.11.2002 06:12:50
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class LinearNormalizeFunction implements UnaryFunction {
    private float k, d;

	/**
	 * Constructor for LinearNormalizeFunction.
	 */
	public LinearNormalizeFunction(int imageMinColor, int imageMaxColor, int minColor, int maxColor) {
		super();
        
        //Berechnen der Parameter der Geradengleichung y = k*x + d
        k = (float)(maxColor-minColor) / (float)(imageMaxColor-imageMinColor);
        d = (float)minColor - k*imageMinColor;        
	}

	/**
	 * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
	 */
	public int process(int color) {
		return Math.round(((float)color)*k + d);
	}

}
