/**
 * TransformFunction.java
 * 
 * Created on 20.12.2002, 14:27:55
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface TransformFunction {
    public void transform(Complex[] data);
}
