/*
 * Transform.java
 *
 * Created on 21. M�rz 2002, 13:27
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmst�tter
 *
 * @version 0.1
 */
public interface Transform {
    
    //Schlechter Stil bei der Benennung. Au�erdem mu� bei
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

