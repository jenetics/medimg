/*
 * WeightPointStrategy.java
 *
 * Created on 26. März 2002, 09:20
 */

package org.wewi.medimg.reg;



/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class WeightPointTransformationImportance extends AbstractTransformationImportance {
    public static final WeightPointTransformationImportance INSTANCE = new WeightPointTransformationImportance();

    /** Creates new WeightPointStrategy */
    public WeightPointTransformationImportance() {
    }
    
	public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
		
        double sumPoints = 0;
        double alpha = 0.0;
        double[] erg = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumPoints += featureNPoints[i];
            }
        }

        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                alpha = featureNPoints[i] / sumPoints;
            } else {
                alpha = 0.0;
            }
            erg[i] = alpha;
        }		
        return erg;
	}    


    
}
