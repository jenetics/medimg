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
public final class IdentityTransform implements Transform {
    
    /** Creates a new instance of IdentityTransform */
    public IdentityTransform() {
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
        return null;
    }    
    
    public Transform scale(double alpha) {
        return null;
    }       
    
    public Transform concatenate(Transform trans) {
        return null;
    }
    
    public Transform invert() {
        return null;
    }
    
    public String toString() {
        return "Identity-Transformation";
    }
}
