/**
 * Created on 14.09.2002
 *
 */
package org.wewi.medimg.image.geom.transform;

/**
 * @author Franz Wilhelmst�tter
 *
 */
public interface Interpolateable {
    public Transformation interpolate(Transformation trans2, double w);
}
