/*
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
public interface Transform {
    
    //Schlechter Stil bei der Benennung. Außerdem muß bei
    //jeder Transformation ein neues double-Array erzeugt werden.
    public double[] transform_fw(double[] source);
    
    public double[] transform_bw(double[] source);
    
    //Sollte so ausschauen
    public void transformForward(float[] source, float[] target);
    
    public void transformBackward(float[] source, float[] target);
    
    public void transformForward(double[] source, double[] target);
    
    public void transformBackward(double[] source, double[] target);
    
    public Image transform(Image source); 
    
    public Transform scale(double alpha);
    
    public Transform concatenate(Transform trans);
    
    public Transform invert();// throw TransformationException();
    

}

