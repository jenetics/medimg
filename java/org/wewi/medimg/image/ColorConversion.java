/*
 * ColorSubstitution.java
 *
 * Created on 17. Jänner 2002, 19:24
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ColorConversion {
    public abstract void convert(int grey, int[] rgb);

    public abstract int convert(int[] rgb);
}
