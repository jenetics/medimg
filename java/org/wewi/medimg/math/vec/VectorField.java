/**
 * Created on 19.11.2002 16:33:14
 *
 */
package org.wewi.medimg.math.vec;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface VectorField {
    
    public void setVector(double[] startPoint, double[] endPoint);

    public VectorIterator getVectorIterator();
}
