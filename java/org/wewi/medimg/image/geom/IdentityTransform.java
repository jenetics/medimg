/*
 * IdentityTransform.java
 *
 * Created on March 26, 2002, 4:09 PM
 */

package org.wewi.medimg.image.geom;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class IdentityTransform implements Transform {
    
    /** Creates a new instance of IdentityTransform */
    public IdentityTransform() {
    }
    
    public void inverseTransform(float[] source, float[] dest) {
        System.arraycopy(source, 0, dest, 0, dest.length);
    }
    
    public void transform(float[] source, float[] dest) {
        System.arraycopy(source, 0, dest, 0, dest.length);
    }
    
}
