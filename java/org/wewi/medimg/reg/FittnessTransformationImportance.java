/*
 * FittnessStrategy.java
 *
 * Created on 26. März 2002, 09:51
 */

package org.wewi.medimg.reg;





/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class FittnessTransformationImportance extends AbstractTransformationImportance {
    public static final FittnessTransformationImportance INSTANCE = new FittnessTransformationImportance();

    /** Creates new FittnessStrategy */
    public FittnessTransformationImportance() {
    }
    
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
        
        double sumFittness = 0;
        double alpha = 0.0;
        double[] erg = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumFittness += similarity[i];
            }
        }

        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                alpha = similarity[i] / sumFittness;
            } else {
                alpha = 0.0;
            }
            erg[i] = alpha;
        }        
        return erg;
    }    
    

}
