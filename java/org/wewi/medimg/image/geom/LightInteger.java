/*
 * LightInteger.java
 *
 * Created on 21. Februar 2002, 22:47
 */

package org.wewi.medimg.image.geom;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class LightInteger implements Comparable {
    private final int value;
    
    public LightInteger(int value) {
        this.value = value;
    }

    public int compareTo(Object obj) {
        LightInteger integer = (LightInteger)obj;
        return (value - integer.value);
    }
    
}
