/*
 * MultiModalImage.java
 *
 * Created on 1. Juli 2002, 20:53
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface MultiModalImage extends Image {
    public int getNoOfModality();
    
    public int getColor(int pos);
    
    public int getColor(int x, int y, int z, int m);
    
    public void setColor(int pos);
    
    public void setColor(int x, int y, int z, int m, int color);
}
