/*
 * ColorSubstitution.java
 *
 * Created on 17. Jänner 2002, 19:24
 */

package org.wewi.medimg.image;

import java.io.Serializable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public interface ColorConversion extends Cloneable, Serializable {
    
    public void convert(int imageValue, int[] pixelComponents);

    public int convert(int[] pixelComponents);
    
    public Object clone();
}
