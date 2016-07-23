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
        double alpha = 0.0;
        double sumValid = 0;
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumValid++;
            }
        }
        if (sumValid == 0) {
            Arrays.fill(result, 0);    
        } else {    
            for (int i = 0; i < features.length; i++) {
                if (similarity[i] >= getErrorLimit()) {
                    alpha = 1 / sumValid;
                } else {
                    alpha = 0.0;
                }
                result[i] = alpha;
            }
        }

        return result;
    }

    
}

