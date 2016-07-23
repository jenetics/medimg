/**
 * Created on 24.09.2002
 *
 */
package org.wewi.medimg.image.geom.transform;

/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public interface InterpolateableTransformation extends  Transformation {
    public InterpolateableTransformation interpolate(InterpolateableTransformation trans, double w);
}
