/*
 * RegularDisplacmentField.java
 *
 * Created on 3. Juli 2002, 00:12
 */

package org.wewi.medimg.image.geom;


import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class RegularDisplacementField implements Transformation {
    private int strideX;
    private int strideY;
    private int strideZ;

    public RegularDisplacementField(int strideX, int strideY, int strideZ) {
        this.strideX = strideX;
        this.strideY = strideY;
        this.strideZ = strideZ;
    }
    
    public Transformation concatenate(Transformation trans) {
        return null;
    }
    
    public Transformation createInverse() {
        return null;
    }
    
    public Transformation scale(double alpha) {
        return null;
    }
    
    public void transform(Image source, Image target) {
    }
    
    public void transform(double[] source, double[] target) {
    }
    
    public void transform(float[] source, float[] target) {
    }
    
    public void transform(int[] source, int[] target) {
    }
    
}
