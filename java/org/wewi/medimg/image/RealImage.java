/**
 * RealImage.java
 * Created on 08.05.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RealImage {
    private float[] data = new float[100];

    /**
     * 
     */
    public RealImage() {
        super();
    }
    
    public double getDoubleColor(int pos) {
        return data[pos];
    }
    
    
    public static void main(String[] args) {
    }

}
