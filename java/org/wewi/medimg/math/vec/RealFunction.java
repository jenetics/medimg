/*
 * RealFunction.java
 *
 * Created on 10. Mai 2002, 21:26
 */

package org.wewi.medimg.math.vec;

import org.wewi.medimg.math.Function;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface RealFunction extends Function {
    public double eval(double arg);
}
