/*
 * Transform.java
 *
 * Created on 21. M�rz 2002, 13:27
 */
package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmst�tter
 *
 * @version 0.1
 */
public interface Transformation {
    public void transform(int[] source, int[] target);
    
    public void transform(float[] source, float[] target);
    
    public void transform(double[] source, double[] target); 
    
    public void transform(Image source, Image target);    
    
    public Transformation scale(double alpha);
    
    public Transformation concatenate(Transformation trans);
    
    public Transformation createInverse();
    

}
