/**
 * Created on 27.11.2002 10:54:18
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public interface OuterEnergy {
    public double energy(Point point);
}
