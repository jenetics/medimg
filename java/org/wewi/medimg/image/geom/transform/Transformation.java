/**
 * Transform.java
 *
 * Created on 21. März 2002, 13:27
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public interface Transformation {
    

    public void transform(int[] source, int[] target);    

    public void transform(float[] source, float[] target);
    
    public void transform(double[] source, double[] target);
    
    public Image transform(Image source); 
    
    public void transform(Image source, Image taget);     
    
    public Transformation scale(double alpha);
    
    public Transformation concatenate(Transformation trans);
    
    public Transformation createInverse();// throw TransformationException();
    

}

