/**
 * Created on 21.11.2002 13:53:29
 *
 */
package org.wewi.medimg.math.vec.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ScaleVectorFunction implements VectorFunction, GridVectorFunction {
    private double scale;

    /**
     * Constructor for ScaleVectorFunction.
     */
    public ScaleVectorFunction(double scale) {
        super();
        this.scale = scale;
    }


    /**
     * @see org.wewi.medimg.math.vec.ops.VectorFunction#transform(double[])
     */
    public void transform(double[] vector) {
        vector[0] *= scale;
        vector[1] *= scale;
        vector[2] *= scale;
    }
    /**
     * @see org.wewi.medimg.math.vec.ops.GridVectorFunction#transform(int, int, int, double[])
     */
    public void transform(int gridX, int gridY, int gridZ, double[] newVector) {
        transform(newVector);
    }

}
