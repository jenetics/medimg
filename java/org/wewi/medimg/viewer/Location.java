/*
 * Location.java
 *
 * Created on 1. Juli 2002, 22:42
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 */
class Location {
    private final int x;
    private final int y;
    
    /** Creates a new instance of Location */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
