/*
 * Transform.java
 *
 * Created on 21. März 2002, 13:27
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  werner weiser
 * @version 
 */
public interface Transform {
    
    public double[] transform_fw(double[] source);
    
    public double[] transform_bw(double[] source);
    
    public Image transform(Image source); 
    
    public Transform scale(double alpha);
    
    public Transform concatenate(Transform trans);
    
    public Transform invert();// throw TransformationException();
    
    public String toString();

}

