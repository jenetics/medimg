/**
 * Created on 07.10.2002 18:07:04
 *
 */
package org.wewi.medimg.util.param;

import java.util.Iterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ParameterIterator extends Iterator, Cloneable {
    public Object clone();
}
