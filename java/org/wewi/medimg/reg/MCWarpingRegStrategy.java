/*
 * MCWarpingRegStrategy.java
 *
 * Created on 16. Mai 2002, 11:34
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.geom.transform.Transform;
import org.wewi.medimg.reg.interpolation.InterpolStrategy;
import org.wewi.medimg.reg.metric.AffinityMetric;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class MCWarpingRegStrategy implements RegStrategy {
    
    private InterpolStrategy weightStrategy;

    private AffinityMetric affinityMetric;
    
    private static final double epsilon = 0.05;

    private static final int DIM = 3;
    
    /** Creates new MCWarpingRegStrategy */
    public MCWarpingRegStrategy(InterpolStrategy strategy, AffinityMetric metric) {
        super();
        weightStrategy = strategy;
        affinityMetric = metric;
    }    
    
    public Transform calculate(RegisterParameter param) throws RegistrationException {
        
        
        return null;
    }
}
