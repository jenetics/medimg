/*
 * WeightPointStrategy.java
 *
 * Created on 26. März 2002, 09:20
 */

package org.wewi.medimg.reg.interpolation;


import org.wewi.medimg.image.geom.transform.TransformVector;


/**
 *
 * @author  werner weiser
 * @version 
 */
public class WeightPointStrategy extends TransformInterpol {

    /** Creates new WeightPointStrategy */
    public WeightPointStrategy() {
    }
    public void calculateWeights() {
        int sumpoints = 0;
        int transforms = transformVector.size();
        int i;

        double alpha = 0.0;
        for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                sumpoints += transformVector.getNPoints(i);
            }
        }

        for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                alpha = ((double)(transformVector.getNPoints(i))) / 
                        ((double)(sumpoints));
            } else {
                alpha = 0.0;
            }
            transWeights[i] = alpha;
        }

    }

    
}
