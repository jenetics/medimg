/*
 * NullImage.java
 *
 * Created on 26. Jänner 2002, 21:08
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Nullable;
import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class NullImage implements Image, Singleton {
    private static NullImage singleton = null;

    private NullImage() {
    }
    
    public static NullImage getInstance() {
        if (singleton == null) {
            singleton = new NullImage();
        }
        return singleton;
    }
    
    public int getColor(int x, int y, int z) {
        return 0;
    }
    
    public int getColor(int pos) {
        return 0;
    }
    
    public void setColor(int pos, int color) {
    }
    
    public void setColor(int x, int y, int z, int color) {
    }
    
    public int getMaxZ() {
        return 0;
    }
    
    public int getMaxY() {
        return 0;
    }
    
    public int getMaxX() {
        return 0;
    }
    
    public int getMinX() {
        return 0;
    }
    
    public int getMinY() {
        return 0;
    }
    
    public int getMinZ() {
        return 0;
    }    
    
    public int getNVoxels() {
        return 0;
    }
    
    public boolean isNull() {
        return true;
    }
    
    public Object clone() {
        //throw new UnsupportedOperationException();
        return getInstance();
    }    
    
    public ImageHeader getHeader() {
        return NullImageHeader.getInstance();
    }
    
}
