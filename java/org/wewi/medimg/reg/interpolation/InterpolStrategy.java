/*
 * InterpolStrategy.java
 *
 * Created on 21. M�rz 2002, 13:23
 */

package org.wewi.medimg.reg.interpolation;

import org.wewi.medimg.image.geom.transform.TransformVector;
import org.wewi.medimg.image.geom.transform.Transformation;
/**
 *
 * @author  werner weiser
 * @version 
 */
public interface InterpolStrategy {
    //virtual ITransform* interpolate(CTransformVector* tv) = 0;
    
    public Transformation interpolate(TransformVector tv);


}

