/*
 * ImageAccess2D.java
 *
 * Created on 27. Jänner 2003, 18:28
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageAccess2D extends ImageAccess {
    public void setColor(int x, int y, int color);
    
    public int getColor(int x, int y);
}
