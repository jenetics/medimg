/*
 * Transform.java
 *
 * Created on 21. März 2002, 13:27
 */
package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public interface Transform {
    public void transform(int[] source, int[] target);
    
    public void transform(float[] source, float[] target);
    
    public void transform(double[] source, double[] target); 
    
    public void transform(Image source, Image target);    
    
    public Transform scale(double alpha);
    
    public Transform concatenate(Transform trans);
    
    public Transform createInverse();
    

}


