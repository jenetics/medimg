/*
 * DisplacementField.java
 *
 * Created on 3. Juli 2002, 00:19
 */

package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class DisplacementField implements Transformation {
    
    /** Creates a new instance of DisplacementField */
    public DisplacementField() {
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
