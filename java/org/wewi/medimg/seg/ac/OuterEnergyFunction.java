/**
 * Created on 14.11.2002 08:47:06
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class OuterEnergyFunction {
    private Image image;

	/**
	 * Constructor for OuterEnergyFunction.
	 */
	public OuterEnergyFunction(Image image) {
		super();
        this.image = image;
	}
    
    public abstract double energy(Point[] ac);

}
