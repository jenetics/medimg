/**
 * Created on 14.11.2002 09:04:25
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VectorFlowOuterEnergy extends OuterEnergyFunction {

    /**
     * Constructor for VectorFlowOuterEnergy.
     * @param image
     */
    public VectorFlowOuterEnergy(Image image) {
        super(image);
    }

    /**
     * @see org.wewi.medimg.seg.ac.OuterEnergyFunction#energy(Point[])
     */
    public double energy(Point[] ac) {
        return 0;
    }

}
