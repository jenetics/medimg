/*
 * Vertex.java
 *
 * Created on March 18, 2002, 4:42 PM
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class Point {
    float x = 0, y = 0, z = 0;
    
    Point() {
    }
    
    Point(float xcoord, float ycoord, float zcoord) {
        x = xcoord;
        y = ycoord;
        z = zcoord;
    }
}
