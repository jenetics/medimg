package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageAccess {
    
    public void setColor(int pos, int color);
    
    public void setColor(int x, int y, int z, int color);
    
    public int getColor(int pos);
    
    public int getColor(int x, int y, int z);
}
