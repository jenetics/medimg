/*
 * Transform.java
 *
 * Created on March 26, 2002, 3:57 PM
 */

package org.wewi.medimg.image.geom;

/**
 *
 * @author  fwilhelm
 */
public interface Transform {
    public void transform(float[] source, float[] dest);
    public void inverseTransform(float[] source, float[] dest);
}
