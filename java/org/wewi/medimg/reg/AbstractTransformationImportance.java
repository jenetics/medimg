package org.wewi.medimg.reg;

/**
 * @author werner weiser
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractTransformationImportance implements TransformationImportance {


    protected double errorLimit = 0.5;
    
	/**
	 * @see org.wewi.medimg.reg.TransformationImportance#transformationWeights(int[], double[], int[])
	 */
	public double[] transformationWeights(int[] features, double[] similarity,
										    int[] featureNPoints) {
		return null;
	}
	
	
    public void setErrorLimit(double limit) {
        errorLimit = limit;
    }
    
    public double getErrorLimit() {
        return errorLimit;
    }	

}
