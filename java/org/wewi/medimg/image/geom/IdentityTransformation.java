/*
 * IdentityTransform.java
 *
 * Created on March 26, 2002, 4:09 PM
 */

package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class IdentityTransformation implements Transformation {
    
    /** Creates a new instance of IdentityTransform */
    public IdentityTransformation() {
    }
    
    public void transform(int[] source, int[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }
    
    public void transform(double[] source, double[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }
    
    public void transform(float[] source, float[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }
    
    public void transformBackward(int[] source, int[] target) {
        System.arraycopy(source, 0, target, 0, target.length);    
    }
    
    public void transformBackward(double[] source, double[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }
    
    public void transformBackward(float[] source, float[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }
    
    public Image transform(Image source) {
        return source;
    }    
    
    public Transformation scale(double alpha) {
        return null;
    }       
    
    public Transformation concatenate(Transformation trans) {
        return this;
    }
    
    public Transformation createInverse() {
        return new IdentityTransformation();
    }
    
    public String toString() {
        return "Identity-Transformation";
    }
    
    public void transform(Image source, Image target) {
    }
    
}
