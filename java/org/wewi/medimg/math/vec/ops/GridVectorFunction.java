/**
 * GridVectorFunction.java
 * 
 * Created on 07.03.2003, 16:05:50
 *
 */
package org.wewi.medimg.math.vec.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface GridVectorFunction {
    public void transform(int gridX, int gridY, int gridZ, double[] newVector);
}
