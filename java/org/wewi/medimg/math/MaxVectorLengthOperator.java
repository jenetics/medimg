/**
 * Created on 21.11.2002 13:50:00
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MaxVectorLengthOperator implements VectorOperator {
    private double maxLength = 0;

	/**
	 * Constructor for MaxVectorLengthOperator.
	 */
	public MaxVectorLengthOperator() {
		super();
	}

	/**
	 * @see org.wewi.medimg.math.VectorOperator#process(double[], double[])
	 */
	public void process(double[] start, double[] end) {
        double length = MathUtil.sqr(end[0] - start[0]) + 
                         MathUtil.sqr(end[1] - start[1]) +
                         MathUtil.sqr(end[2] - start[2]);
        if (maxLength < length) {
            maxLength = length;    
        }
	}
    
    public double getMaxLength() {
        return Math.sqrt(maxLength);    
    }

}
