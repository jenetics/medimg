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
public class Point {
    private final float x;
    private final float y;
    private final float z;
    
    /** Creates a new instance of Vertex */
    public Point(float xcoord, float ycoord, float zcoord) {
        x = xcoord;
        y = ycoord;
        z = zcoord;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public float[] getCoordinates() {
        float[] result = new float[3];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(x);
        buffer.append(",").append(y);
        buffer.append(",").append(z).append(")");
        return buffer.toString();
    }
}
