/**
 * AffinityMetric.java
 *
 * Created on 26. März 2002, 10:04
 */

package org.wewi.medimg.reg.metric;

import org.wewi.medimg.image.geom.transform.Transformation;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public interface AffinityMetric {
    
    public double similarity();
    
    public void setSourceTissueData(TissueData sfi);

    public void setTargetTissueData(TissueData sfi);

    public void setTransformation(Transformation transformation);

}

