/**
 * MultifractalMeasure.java
 *
 * Created on 23. Jänner 2003, 21:00
 */

package org.wewi.medimg.image.geom.fractal;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class MultifractalMeasure {
    
    /** Creates a new instance of MultifractalMeasure */
    public MultifractalMeasure() {
    }
    
    
    public abstract double dimension(Image image);
}
