package org.wewi.medimg.reg;

import java.util.Arrays;


/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1  
 */
public class ConstantTransformationImportance extends AbstractTransformationImportance {

    public static final ConstantTransformationImportance INSTANCE = new ConstantTransformationImportance();

	/**
	 * Constructor for ConstantTransformationImportance.
	 */
	public ConstantTransformationImportance() {
		super();
	}

	/**

	/**
	 * @see org.wewi.medimg.reg.TransformationImportance#transformationWeights(int[], double[], int[])
	 */
	public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
		double[] result = new double[features.length];
        Arrays.fill(result, 1d/(double)features.length);
        return result;
	}

    
}

