/*
 * FittnessStrategy.java
 *
 * Created on 26. März 2002, 09:51
 */

package org.wewi.medimg.reg.interpolation;



/**
 *
 * @author  werner weiser
 * @version 
 */
public class FittnessStrategy extends TransformInterpol {

    /** Creates new FittnessStrategy */
    public FittnessStrategy() {
    }
    
    public void calculateWeights() {
        double sumFittness = 0;
        int transforms = transformVector.size();
        int i;
        double alpha = 0.0;
        for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                sumFittness += transformVector.getFitness(i);
            }
        }

        for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                alpha = transformVector.getFitness(i) / 
                        sumFittness;
            } else {
                alpha = 0.0;
            }
            transWeights[i] = alpha;
        }
    }

}
