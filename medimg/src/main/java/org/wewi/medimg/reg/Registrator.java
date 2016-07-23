/*
 * RegStrategy.java
 *
 * Created on 21. März 2002, 11:19
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.transform.Transformation;
/**
 *
 * @author  Werner Weiser
 * @version 0.2
 */
public interface Registrator { 
    
    public Transformation registrate(Image source, Image target);

}

