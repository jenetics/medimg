/**
 * Point.java
 *
 * Created on 18. Februar 2002, 20:19
 */

package org.wewi.medimg.image.geom;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface Point extends Cloneable {
    
    public int getDimensions();
    
    public int getOrdinate(int i);
    
    public boolean equals(Object o);
    
    public Object clone();
    
}

