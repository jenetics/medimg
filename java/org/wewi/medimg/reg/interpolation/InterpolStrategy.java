/*
 * InterpolStrategy.java
 *
 * Created on 21. März 2002, 13:23
 */

package org.wewi.medimg.reg.interpolation;

import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.reg.TransformVector;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public interface InterpolStrategy {    
    public Transformation interpolate(TransformVector tv);
}

