/*
 * DisplacementField.java
 *
 * Created on 13. April 2002, 10:23
 */

package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class DisplacementField implements Transform {

    /** Creates new DisplacementField */
    public DisplacementField() {
        
    }
    
    public double[] transform_fw(double[] source) {
        return null;
    }
    
    public double[] transform_bw(double[] source) {
        return null;
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
    
    public Transform invert() {// throw TransformationException();
        return null;
    }
    
    public String toString() {
        return null;
    }    

}
