/*
 * InterpolStrategy.java
 *
 * Created on 21. März 2002, 13:23
 */

package org.wewi.medimg.reg.interpolation;

import org.wewi.medimg.image.geom.transform.Transform;
import org.wewi.medimg.image.geom.transform.TransformVector;
/**
 *
 * @author  werner weiser
 * @version 
 */
public interface InterpolStrategy {
    //virtual ITransform* interpolate(CTransformVector* tv) = 0;
    
    public Transform interpolate(TransformVector tv);


}

