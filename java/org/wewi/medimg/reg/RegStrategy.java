/*
 * RegStrategy.java
 *
 * Created on 21. M�rz 2002, 11:19
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.geom.transform.Transform;
/**
 *
 * @author  werner weiser
 * @version 
 */
public interface RegStrategy {
    
    // ITransform  calculate(CRegisterParameter& param) throw (ERegistrationException) = 0;
    public Transform calculate(RegisterParameter param) throws RegistrationException; 

}
