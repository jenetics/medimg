/*
 * ConstantAffinityMetric.java
 *
 * Created on 26. März 2002, 15:09
 */

package org.wewi.medimg.reg.metric;

import org.wewi.medimg.image.geom.transform.Transform;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class ConstantAffinityMetric implements AffinityMetric {
    
    public boolean initialized;
    /** Creates new ConstantAffinityMetric */
    public ConstantAffinityMetric() {
        initialized = false;
    }

    public double similarity() {
        return 1.0;
    }

    public void setSourceTissueData(TissueData sf) {
        initialized = false;
    }

    public void setTargetTissueData(TissueData sf) {
        initialized = false;
    }

    public void setTransformation(Transform transform) {
            initialized = false;
    }
    

    
}
